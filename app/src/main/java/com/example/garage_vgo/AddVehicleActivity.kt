package com.example.garage_vgo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.garage_vgo.databinding.ActivityAddvehicleBinding

class AddVehicleActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddvehicleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddvehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vehicleOptions = resources.getStringArray(R.array.Vehiculos)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, vehicleOptions)
        binding.spinnerCarOrMotId.adapter = adapter

        binding.spinnerCarOrMotId.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position == 0){
                    val carOptions = resources.getStringArray(R.array.KmCarro)
                    val carAdapter = ArrayAdapter(this@AddVehicleActivity, android.R.layout.simple_spinner_item, carOptions)
                    binding.spinnerKmCambAceiteId.adapter = carAdapter
                }else{
                    val bikeOptions = resources.getStringArray(R.array.KmMoto)
                    val bikeAdapter = ArrayAdapter(this@AddVehicleActivity, android.R.layout.simple_spinner_item, bikeOptions)
                    binding.spinnerKmCambAceiteId.adapter = bikeAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.tPropiedadId.setOnClickListener { goAddImage() }
        binding.TecnicoMecId.setOnClickListener { goAddImage() }
        binding.soatId.setOnClickListener { goAddImage() }
        binding.otrosImgId.setOnClickListener { goAddImage() }

        binding.buttonSaveId.setOnClickListener {
            goToProfile()
        }
    }

    // Method that directs the user to the Add Image Activity
    private fun goAddImage(){
        val addImage = Intent(this, AddImageActivity::class.java)
        startActivity(addImage)
    }

    // Method that directs the user to the Add Image Activity
    private fun goToProfile(){
        val profile = Intent(this, NavigationActivity::class.java)
        startActivity(profile)
        finish()
    }
}