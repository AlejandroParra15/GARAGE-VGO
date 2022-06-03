package com.example.garage_vgo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.example.garage_vgo.databinding.ActivityAddImgBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddImgBinding
    private var uriPath : String = ""
    //
    private val fileResult = 1
    private lateinit var auth : FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var uid : String
    private val database = FirebaseFirestore.getInstance()
    private val myRef = database.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddImgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        uid = user.uid

        binding.buttonCamera.setOnClickListener {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        binding.buttonGalery.setOnClickListener {
            fileManager()
        }
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        intent.type = "*/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }
                }else {
                    val uri = data.data
                    uri?.let { fileUpload(it) }
                }
            }
        }
    }

    private fun fileUpload(mUri: Uri) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child(uid)
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)
                val tipo:String = intent.getStringExtra("documento").toString()

                myRef.document(uid).collection("documentos").document(tipo).set(hashMap)
                showAlert("Se han cargado los archivos correctamente!")
            }
        }.addOnFailureListener {
            Log.i("message", "file upload error")
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val imageBitmap = intent?.extras?.get("data") as Bitmap
                binding.imageView.setImageBitmap(imageBitmap)
            }
        }

    private fun pickPhoto(){
        val intent=Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type="image/*"
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startForActivityGallery.launch(intent)
    }

    private val startForActivityGallery=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->

        if(result.resultCode== Activity.RESULT_OK){
            val uri = result.data?.data!!
            this.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            binding.imageView.setBackgroundColor(Color.TRANSPARENT)
            binding.imageView.setImageURI(uri)
            uriPath = uri.toString()
        }
    }

    private fun requestPermission() {

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED->{
                }
                else->{
                    val PERMISSIONS = arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    )
                    requestPermissionLauncher.launch(PERMISSIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher=registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){isGranted->
        if(!isGranted.containsValue(false)){

        }else{
            Toast.makeText(this,"NecesitaPermisos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAlert(msg : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mensaje:")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}