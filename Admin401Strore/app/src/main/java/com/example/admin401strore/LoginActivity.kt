@file:Suppress("DEPRECATION")

package com.example.admin401strore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admin401strore.Model.UserModel
import com.example.admin401strore.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private var userName: String? = null
    private var nameOfRestaurant: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googlesignInclient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        val googleSigninoption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        //Initialize Firebase Auth
        auth = Firebase.auth
        //Initialize Firebase database
        database = Firebase.database.reference
        //Initializee google sign in
        googlesignInclient = GoogleSignIn.getClient(this, googleSigninoption)


        binding.btnLogin.setOnClickListener {
            email = binding.edtEmail.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createUserAccount(email, password)
            }
        }

        binding.btnGoogle.setOnClickListener {
            val signI = googlesignInclient.signInIntent
            launcher.launch(signI)
        }

        binding.txtDontHaveAcc.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                updateUI(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        Toast.makeText(this, "Create User & Login Successfully", Toast.LENGTH_SHORT)
                            .show()
                        saveUserData()
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "createAccount: Authentication failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.edtEmail.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()

        val url: String = "https://strore-8515a-default-rtdb.asia-southeast1.firebasedatabase.app"
        var ref = FirebaseDatabase.getInstance(url)
        var rtref = ref.getReference()

        val user = UserModel(userName, nameOfRestaurant, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            rtref.child("user").child(it).setValue(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            //Successfully sign in with Google
                            Toast.makeText(
                                this,
                                "Successfully sign in with Google",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        } else {
                            Toast.makeText(this, "Google Sign-in Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                Toast.makeText(this, "Google Sign-in Failed", Toast.LENGTH_SHORT).show()
            }
            }
        }
}