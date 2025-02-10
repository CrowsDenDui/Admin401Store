package com.example.admin401strore

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admin401strore.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.edtAdName.isEnabled = false
        binding.edtAdAddress.isEnabled = false
        binding.edtAdEmail.isEnabled = false
        binding.edtAdPhone.isEnabled = false
        binding.edtAdPassword.isEnabled = false

        var isEnable = false
        binding.edtButton.setOnClickListener {
            isEnable = ! isEnable

            binding.edtAdName.isEnabled = isEnable
            binding.edtAdAddress.isEnabled = isEnable
            binding.edtAdEmail.isEnabled = isEnable
            binding.edtAdPhone.isEnabled = isEnable
            binding.edtAdPassword.isEnabled = isEnable

            if (isEnable){
                binding.edtAdName.requestFocus()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}