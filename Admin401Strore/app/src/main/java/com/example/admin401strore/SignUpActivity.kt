package com.example.admin401strore

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admin401strore.Model.UserModel
import com.example.admin401strore.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password:  String
    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
    private lateinit var database: DatabaseReference

    private val binding : ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize Firebase Auth
        auth = Firebase.auth
        //initialize Firebase Database
        database = Firebase.database.reference

        binding.btnCreate.setOnClickListener {

            //get text from edit text
            userName = binding.edtName.text.toString().trim()
            nameOfRestaurant = binding.edtRestaurantName.text.toString().trim()
            email = binding.edtEmail.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()

            if(userName.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email, password)
            }
        }
        binding.txtAlreadyHaveAcc.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        val locationList = arrayOf("Tp Hồ Chí Minh", "Hà Nội", "Long An", "Cà Mau")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.edtLocation
        autoCompleteTextView.setAdapter(adapter)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
            else{
                Toast.makeText(this, "Account created fail", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Faillure", task.exception)
            }
        }
    }

    //save data into database
    private fun saveUserData() {
        //get text from edit text
        userName = binding.edtName.text.toString().trim()
        nameOfRestaurant = binding.edtRestaurantName.text.toString().trim()
        email = binding.edtEmail.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()
        val url: String = "https://strore-8515a-default-rtdb.asia-southeast1.firebasedatabase.app"
        val user = UserModel(userName, nameOfRestaurant, email, password)
        val userId : String = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data Firebase database
        var ref = FirebaseDatabase.getInstance(url)
        var rtref = ref.getReference()
        rtref.child("user").child(userId).setValue(user)
    }
}