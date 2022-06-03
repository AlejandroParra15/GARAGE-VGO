package com.example.garage_vgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.garage_vgo.databinding.ActivityKilometrajeCambioDeAceiteBinding

class KilometrajeCambioDeAceite : AppCompatActivity() {

    private lateinit var binding: ActivityKilometrajeCambioDeAceiteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKilometrajeCambioDeAceiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carOptions = resources.getStringArray(R.array.KmCarro)
        val carAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carOptions)
        binding.kilometrajeId.adapter = carAdapter

        binding.saveCambioAceiteId.setOnClickListener {  }

        binding.cancelarId.setOnClickListener { finish() }

    }
}