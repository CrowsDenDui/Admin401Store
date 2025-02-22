package com.example.admin401strore

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder
import com.example.admin401strore.Model.Allmenu
import com.example.admin401strore.databinding.ActivityAddItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firestore.v1.FirestoreProto
import java.io.ByteArrayOutputStream
import java.util.Base64

class AddItemActivity : AppCompatActivity() {

    //food item details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private lateinit var foodImgUri: String

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val url: String = "https://strore-8515a-default-rtdb.asia-southeast1.firebasedatabase.app"

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        //initialize Firebase
        auth = FirebaseAuth.getInstance()
        //initialize Firebase database instance
        database = FirebaseDatabase.getInstance(url)

        binding.btnAddItem.setOnClickListener {
            //get data from file
            foodName = binding.edtAddFoodName.text.toString().trim()
            foodPrice = binding.edtAddFoodPrice.text.toString().trim()
            foodDescription = binding.edtAddDes.text.toString().trim()
            foodIngredient = binding.edtAddIngredient.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtSelectImg.setOnClickListener {
            pickImg.launch("image/*")
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadData() {
        //get a reference to the "menu" node in the database
        val menuRef: DatabaseReference = database.getReference("menu")
        //Generate a unique key for the new menu item
        val newItemKey: String? = menuRef.push().key

        if(foodImgUri != null) {

            //Create a new menu item
            val newItem = Allmenu(
                foodName = foodName,
                foodPrice = foodPrice,
                foodDescription = foodDescription,
                foodIngredient = foodIngredient,
                foodImg = foodImgUri
            )
            newItemKey?.let { key ->
                menuRef.child(key).setValue(newItem).addOnSuccessListener {
                    Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "Data uploaded failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private val pickImg =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val bitmap = uriToBitmap(it)
                foodImgUri = encodeImg(bitmap)
                if (uri != null){
                    binding.ivSelectImage.setImageURI(uri)
                }
            }
        }


    @SuppressLint("NewApi")
    private fun encodeImg(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return String(Base64.getEncoder().encode(imageBytes))
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        val inputStream = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun selectedImgFromGal(){
        pickImg.launch("image/*")
    }
}
