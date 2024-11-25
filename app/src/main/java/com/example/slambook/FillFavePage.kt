package com.example.slambook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivityFillFavePageBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FillFavePage : AppCompatActivity() {

    private lateinit var binding: ActivityFillFavePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityFillFavePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Done button click listener
        binding.buttonDone.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        // Get data from EditTexts using ViewBinding
        val sports = binding.editTextSports.text.toString()
        val color = binding.editTextColor.text.toString()
        val food = binding.editTextFood.text.toString()
        val comfortFood = binding.editTextCf.text.toString()
        val place = binding.editTextPlace.text.toString()
        val hobbies = binding.editTextHobbies.text.toString()

        // Check if any of the fields are empty
        if (sports.isEmpty() || color.isEmpty() || food.isEmpty() || comfortFood.isEmpty() || place.isEmpty() || hobbies.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
        } else {


            // Store the data in SharedPreferences
            val sharedPreferences = getSharedPreferences("SlamBookPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("COLOR",color)
            editor.putString("FOOD", food)
            editor.putString("COMFORT FOOD", comfortFood)
            editor.putString("PLACE", place)
            editor.putString("HOBBIES", hobbies)
            editor.putString("SPORTS", sports)
            editor.apply()

                val intent = Intent(this, HomeActivity::class.java)  // Replace with your target activity
                startActivity(intent)

                // Optionally, display a toast message or perform other actions
                Toast.makeText(this, "Information has been saved", Toast.LENGTH_SHORT).show()
                finish()  // Finish the current activity (if necessary)
            }
        }
    }
