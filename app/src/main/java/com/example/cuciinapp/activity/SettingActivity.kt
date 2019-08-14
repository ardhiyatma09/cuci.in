package com.example.cuciinapp.activity

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cuciinapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.ubah_activity.*
import java.io.IOException
import java.util.*

class SettingActivity : AppCompatActivity() {

    lateinit var fAuth: FirebaseAuth
    lateinit var helperPrefs: PrefsHelper
    lateinit var dbRef: DatabaseReference
    lateinit var filePath: Uri
    lateinit var stoRef: StorageReference
    lateinit var fstorage: FirebaseStorage

    val mAuth = FirebaseAuth.getInstance()
    val REQUEST_IMAGE = 10002
    val PERMISSION_REQUEST_CODE = 1001
    val PICK_IMAGE_REQUEST = 900


    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubah_activity)

        fAuth = FirebaseAuth.getInstance()
        helperPrefs = PrefsHelper(this)
        fstorage = FirebaseStorage.getInstance()
        stoRef = fstorage.reference

        Glide.with(this)
            .load(R.drawable.avatar)
            .into(img_upload)

        img_upload.setOnClickListener {
            when {
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            PERMISSION_REQUEST_CODE
                        )
                    } else {
                        imageChooser()
                    }
                }
                else -> {
                    imageChooser()
                }
            }

        }



        FirebaseDatabase.getInstance().getReference("Akun/${fAuth.uid}")
            .child("Nama").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    nama_update.setText(p0.value.toString())
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })

        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${helperPrefs.getUI()}")
        dbRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("uid", helperPrefs.getUI())
                if (p0.child("/bukti").value.toString() != "null") {
                    Glide.with(this@SettingActivity)
                        .load(p0.child("/bukti").value.toString())
                        .into(img_upload)
                }
                nama_update.setText(p0.child("/Nama").value.toString())
                alamat_update.setText(p0.child("/Alamat").value.toString())
                kontak_update.setText(p0.child("/Kontak").value.toString())
                email_update.setText(p0.child("/Email").value.toString())
            }
        })
        btn_update.setOnClickListener {
            val uidUser = fAuth.currentUser?.uid

            dbRef = FirebaseDatabase.getInstance().reference
            val update_nama = nama_update.text.toString()
            val update_alamat = alamat_update.text.toString()
            val update_kontak = kontak_update.text.toString()
            val update_email = email_update.text.toString()

            if (!update_alamat.isNotEmpty() && !update_email.isNotEmpty() && !update_nama.isNotEmpty()) {
                simpanToFireBase()
            }


            dbRef.child("Akun/$uidUser/Nama").setValue(update_nama)
            dbRef.child("Akun/$uidUser/Alamat").setValue(update_alamat)
            dbRef.child("Akun/$uidUser/Kontak").setValue(update_kontak)
            dbRef.child("Akun/$uidUser/Email").setValue(update_email)
            Toast.makeText(this, "Sukses!!", Toast.LENGTH_SHORT).show()
            finish()

        }
    }

    private fun imageChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_IMAGE)
    }


    private fun chooseFile() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this@SettingActivity, "Oops! Permission Denied!!", Toast.LENGTH_SHORT).show()
                else
                    chooseFile()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_IMAGE -> {
                filePath = data!!.data
                uploadFile()
                try {
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver, filePath
                    )
                    Glide.with(this)
                        .load(bitmap)
                        .into(img_upload)
                } catch (x: IOException) {
                    x.printStackTrace()
                }

            }
        }
    }


    fun GetFileExtension(uri: Uri): String? {
        val contentResolverz = this.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contentResolverz.getType(uri))
    }

    private fun uploadFile() {
        val progress = ProgressDialog(this).apply {
            setTitle("Uploading Picture....")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
        val data = FirebaseStorage.getInstance()
        var value = 0.0

        val user = mAuth.currentUser
        val uid = helperPrefs.getUI()
        val nameX = UUID.randomUUID().toString()
        val ref: StorageReference = stoRef
//            .child("images/$uid/${nameX}.${GetFileExtension(filePath)}")
        var storage = data.reference.child("Image_Profile/$nameX").putFile(filePath)
//       ref.putFile(filePath)
            .addOnProgressListener { taskSnapshot ->
                value = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
            }
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    simpanToFireBase()
                }
                Toast.makeText(this@SettingActivity, "berhasil upload", Toast.LENGTH_SHORT).show()
                progressDownload.visibility = View.GONE
                progress.hide()
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }

    }

    private fun simpanToFireBase() {
//        dbRef = FirebaseDatabase.getInstance().getReference("Akun")
//        dbRef.child("imageName").setValue(namex)
//        dbRef.child("fileurl").setValue(fileurl)
//
//        Toast.makeText(this, "Data berhasil ditambah",
//            Toast.LENGTH_SHORT).show()

        val nameXXX = UUID.randomUUID().toString()
        val iddest = helperPrefs.getCounterId()
        val uid = fAuth.currentUser?.uid
        val storageRef: StorageReference = stoRef
            .child("images/$uid/$nameXXX.${GetFileExtension(filePath)}")
        storageRef.putFile(filePath).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                dbRef = FirebaseDatabase.getInstance().getReference("Akun/$uid")
                dbRef.child("bukti").setValue(it.toString())
                FirebaseDatabase.getInstance().getReference("Akun/")
                    .child("${fAuth.uid}/id")
                    .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {
                                dbRef.child("iduser").setValue(p0.value)
                            }

                            override fun onCancelled(p0: DatabaseError) {
                                Log.e("Error", p0.message)
                            }

                        })
            }
//            Toast.makeText(
//                this,
//                "Success Upload",
//                Toast.LENGTH_SHORT
//            ).show()
            progressDownload.visibility = View.GONE
        }.addOnFailureListener {
            Log.e("TAG_ERROR", it.message)
        }.addOnProgressListener { taskSnapshot ->
            //            progressDownload.visibility = View.VISIBLE
        }

    }
}

