package com.example.garage_vgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage_vgo.databinding.ActivityNotificationPicoYplacaBinding

class ActivityNotificationPicoYPlaca : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationPicoYplacaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationPicoYplacaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}