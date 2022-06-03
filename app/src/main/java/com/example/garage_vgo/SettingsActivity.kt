package com.example.garage_vgo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage_vgo.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        binding.logoutBtn.setOnClickListener {
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)
            finish()
        }
    }
}