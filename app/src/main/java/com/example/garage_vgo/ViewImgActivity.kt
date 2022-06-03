package com.example.garage_vgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage_vgo.databinding.ActivityViewImgBinding

class ViewImgActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImgBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}