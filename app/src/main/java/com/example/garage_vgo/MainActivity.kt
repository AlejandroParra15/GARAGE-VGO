package com.example.garage_vgo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.garage_vgo.databinding.ActivityMainBinding
import com.example.garage_vgo.databinding.DialogForgotPasswordBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var GOOGLE_SIGN_IN = 100
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding initialize
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("EMAIL",null)
        if(email != null){
            goHome()
        }

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

        binding.btGoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("179751294138-dnibq3l16s3ds8l0g75kq8unb6n0kpri.apps.googleusercontent.com")
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun emptyValidation(){
        // login with username/password
        if(binding.etUser.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
            auth.signInWithEmailAndPassword(binding.etUser.text.toString(),
                binding.etPassword.text.toString()).addOnCompleteListener(){
                // validate if the authentication is successful
                    if(it.isSuccessful){
                        saveName()
                        val editor : SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("EMAIL",binding.etUser.text.toString())
                        editor.apply()
                        goHome()
                    } else {
                        //otherwise, display an alert
                        showAlert()
                    }
                }
        }
    }

    private fun saveName(){
        var db = FirebaseFirestore.getInstance()
        val user = auth.currentUser
        val uid = user!!.uid

        db.collection("users").document(uid!!).get().addOnCompleteListener {
            val name = it.result.get("nombre") as String
            val lastName = it.result.get("apellido") as String
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("NAME","$name $lastName")
            editor.apply()
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
        val homeIntent = Intent(this, NavigationActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    // Method that directs the user to the home activity
    private fun goRegisterActivity(){
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null){

                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)

                    auth.signInWithCredential(credential).addOnCompleteListener(){
                        // validate if the authentication is successful
                        if(it.isSuccessful){
                            saveName()
                            goHome()
                        } else {
                            //otherwise, display an alert
                            showAlert()
                        }
                    }
                }
            } catch (e : ApiException){
                showAlert()
            }

        }

    }

}