package com.example.slambook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivityInfoSlamBinding

class InfoSlam : AppCompatActivity() {

    private lateinit var binding: ActivityInfoSlamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSlamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.name.text.toString().trim()
        val age = binding.editTextAge.text.toString().trim()
        val extraField = binding.editTextText5.text.toString().trim()

        if (name.isEmpty() || age.isEmpty() || extraField.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data submitted successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }
