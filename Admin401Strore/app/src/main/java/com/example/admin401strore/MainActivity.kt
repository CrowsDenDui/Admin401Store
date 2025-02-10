package com.example.admin401strore

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admin401strore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnAddMenu.setOnClickListener {
            val i = Intent(this, AddItemActivity::class.java)
            startActivity(i)
        }

        binding.btnAllItemMenu.setOnClickListener {
            val i = Intent(this, AllItemActivity::class.java)
            startActivity(i)
        }

        binding.btnOrderDispatch.setOnClickListener {
            val i = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(i)
        }

        binding.btnProfile.setOnClickListener {
            val i = Intent(this, AdminProfileActivity::class.java)
            startActivity(i)
        }

        binding.btnCreateNewUser.setOnClickListener {
            val i = Intent(this, CreateUserActivity::class.java)
            startActivity(i)
        }

        binding.txtPendingOrder.setOnClickListener{
            val i = Intent(this, PendingOrderActivity::class.java)
            startActivity(i)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}