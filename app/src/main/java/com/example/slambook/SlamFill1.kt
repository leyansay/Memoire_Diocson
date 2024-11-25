package com.example.slambook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivitySlamFill1Binding

class SlamFill1 : AppCompatActivity() {

    private lateinit var binding: ActivitySlamFill1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySlamFill1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the "Next" button to save data
        binding.nextButton.setOnClickListener {
            saveData()
        }
    }

    // Function to save data in SharedPreferences
    private fun saveData() {
        val name = binding.name.text.toString()
        val nickname = binding.editTextText5.text.toString()
        val age = binding.editTextAge.text.toString()
        val gender = binding.spinnergender.selectedItem.toString()
        val hometown = binding.editTextHometown.text.toString()

        // Validation for empty fields
        if (name.isEmpty() || nickname.isEmpty() || age.isEmpty() || gender.isEmpty() || hometown.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
        } else {
            // Validate age input
            val ageInt = age.toIntOrNull()
            if (ageInt == null) {
                Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show()
            } else {
                // Save user data in SharedPreferences
                val sharedPreferences = getSharedPreferences("SlamBookPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("NAME", name)
                editor.putString("NICKNAME", nickname)
                editor.putInt("AGE", ageInt)
                editor.putString("GENDER", gender)
                editor.putString("HOMETOWN", hometown)
                editor.apply() // Save changes asynchronously

                // Navigate to the next activity
                val toHome = Intent(this, FillFavePage::class.java)
                startActivity(toHome)
            }
        }
    }
}
