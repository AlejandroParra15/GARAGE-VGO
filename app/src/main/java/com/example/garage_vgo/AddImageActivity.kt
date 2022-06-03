package com.example.garage_vgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.garage_vgo.databinding.ActivityAddImgBinding

class AddImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddImgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddImgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCamera.setOnClickListener {

        }

        binding.buttonGalery.setOnClickListener {

        }
    }
}