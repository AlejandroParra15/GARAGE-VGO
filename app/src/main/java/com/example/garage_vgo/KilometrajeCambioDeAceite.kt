package com.example.garage_vgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage_vgo.databinding.ActivityKilometrajeCambioDeAceiteBinding

class KilometrajeCambioDeAceite : AppCompatActivity() {

    private lateinit var binding: ActivityKilometrajeCambioDeAceiteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKilometrajeCambioDeAceiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}