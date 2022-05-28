package com.example.garage_vgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.garage_vgo.databinding.ActivityMainBinding
import com.example.garage_vgo.databinding.DialogForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding initialize
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        auth = FirebaseAuth.getInstance()
        //
        binding.btLogIn.setOnClickListener {emptyValidation()}

        binding.tvCreateAccount.setOnClickListener{goRegisterActivity()}

        binding.tvForgotPassword.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            val view = DialogForgotPasswordBinding.inflate(layoutInflater)
            builder.setView(view.root)
            val dialog = builder.create()
            dialog.show()
            val email = view.etForgotPassword.text
            val btn = view.btSendEmail

            btn.setOnClickListener {
                auth.sendPasswordResetEmail(email.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Correo enviado!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }

    private fun emptyValidation(){
        // login with username/password
        if(binding.etUser.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
            auth.signInWithEmailAndPassword(binding.etUser.text.toString(),
                binding.etPassword.text.toString()).addOnCompleteListener(){
                // validate if the authentication is successful
                    if(it.isSuccessful){
                        goHome()
                    } else {
                        //otherwise, display an alert
                        showAlert()
                    }
                }
        }
    }

    // Method to show an error alert when authenticating
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Method that directs the user to the home activity
    private fun goHome(){
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }

    // Method that directs the user to the home activity
    private fun goRegisterActivity(){
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }

}