package com.example.garage_vgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.garage_vgo.databinding.ActivityNotificationPicoYplacaBinding

class ActivityNotificationPicoYPlaca : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationPicoYplacaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationPicoYplacaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dayOptions = resources.getStringArray(R.array.days)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayOptions)
        binding.spinnerPicoyPlaca.adapter = adapter

        var ciudad = binding.etCiudad.text.toString()

        binding.btEnviarPico.setOnClickListener {  }

        binding.btCancelarPico.setOnClickListener { finish() }

    }
}