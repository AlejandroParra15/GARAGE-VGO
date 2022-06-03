package com.example.garage_vgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage_vgo.databinding.ActivityNotificationSoatTecnicoImpueBinding

class NotificationSoatTecnicoImpue : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationSoatTecnicoImpueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSoatTecnicoImpueBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}