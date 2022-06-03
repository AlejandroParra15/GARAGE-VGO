package com.example.garage_vgo

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.garage_vgo.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var datePickerDialog : DatePickerDialog
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()

        //

        binding.etDate.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)

            val mMonth: Int = c.get(Calendar.MONTH)

            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)

            datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.etDate.setText(
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        binding.btRegister.setOnClickListener{registerUser()}
    }

    private fun registerUser(){
        val email = binding.etEmailRegister.text.toString()
        val name = binding.etName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val date = binding.etDate.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        if(email.isNotEmpty() && name.isNotEmpty() && lastName.isNotEmpty() && date.isNotEmpty() && password.isNotEmpty()){
            if(password.length >= 6){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(){
                    // validate if the authentication is successful
                    if(it.isSuccessful){

                        val user = auth.currentUser

                        val uid = user!!.uid

                        val map = hashMapOf(
                            "nombre" to name,
                            "apellido" to lastName,
                            "fechanacimiento" to date,
                            "correo" to email
                        )

                        val db = FirebaseFirestore.getInstance()

                        db.collection("users").document(uid).set(map).addOnSuccessListener {
                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("EMAIL",email)
                            editor.apply()
                            goRegisterVehicle()
                            Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                showAlert("Se ha producido un error guardando la información del usuario")
                            }
                    } else {
                        //otherwise, display an alert showAlert()
                        showAlert("Se ha producido un error autenticando al usuario")
                    }
                }
            }else{
                showAlert("La contraseña debe tener 6 carácteres o más.")
            }
        }else{
            showAlert("Debe llenar todos los campos del registro.")
        }
    }

    // Method to show an error alert when authenticating
    private fun showAlert(msg : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Method that directs the user to the home activity
    private fun goRegisterVehicle(){
        val addVehicleActivity = Intent(this, AddVehicleActivity::class.java)
        startActivity(addVehicleActivity)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        } else {

        }
    }

    private fun reload() {

    }

}