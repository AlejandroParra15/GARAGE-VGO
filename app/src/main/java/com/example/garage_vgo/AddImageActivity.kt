package com.example.garage_vgo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.setPadding
import com.example.garage_vgo.databinding.ActivityAddImgBinding
import java.io.File

class AddImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddImgBinding
    private var uriPath : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddImgBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        binding.buttonCamera.setOnClickListener {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        binding.buttonGalery.setOnClickListener {
            pickPhoto()
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
}