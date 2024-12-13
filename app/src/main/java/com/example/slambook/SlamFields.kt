package com.example.slambook

import PersonalInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivitySlamFieldsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class SlamFields : AppCompatActivity() {
    private lateinit var binding: ActivitySlamFieldsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlamFieldsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Spinner with gender options
        val genderOptions = resources.getStringArray(R.array.gender_options)
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)
        binding.spinnergender.adapter = genderAdapter

        binding.buttonDone.setOnClickListener {
            // Retrieve input values
            val name = binding.name.text.toString().trim()
            val age = binding.editTextAge.text.toString().trim()
            val nickname = binding.editTextText5.text.toString().trim()
            val hometown = binding.editTextHometown.text.toString().trim()
            val gender = binding.spinnergender.selectedItem.toString().trim()
            val color = binding.editTextColor.text.toString().trim()
            val comfort = binding.editTextPlace.text.toString().trim()
            val food = binding.editTextFood.text.toString().trim()
            val hobbies = binding.editTextHobbies.text.toString().trim()
            val sports = binding.editTextSports.text.toString().trim()

            var isValid = true

            // Use when to validate each field
            when {
                name.isEmpty() -> {
                    binding.name.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.name.error = null
            }

            when {
                nickname.isEmpty() -> {
                    binding.editTextText5.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextText5.error = null
            }

            when {
                hometown.isEmpty() -> {
                    binding.editTextHometown.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextHometown.error = null
            }

            when {
                age.length != 2 -> {
                    binding.editTextAge.error = "Age must be exactly 2 digits"
                    isValid = false
                }
                else -> binding.editTextAge.error = null
            }

            when {
                gender.isEmpty() -> {
                    Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
                    isValid = false
                }
            }

            when {
                color.isEmpty() -> {
                    binding.editTextColor.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextColor.error = null
            }

            when {
                comfort.isEmpty() -> {
                    binding.editTextPlace.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextPlace.error = null
            }

            when {
                food.isEmpty() -> {
                    binding.editTextFood.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextFood.error = null
            }

            when {
                hobbies.isEmpty() -> {
                    binding.editTextHobbies.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextHobbies.error = null
            }

            when {
                sports.isEmpty() -> {
                    binding.editTextSports.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.editTextSports.error = null
            }

            // If all fields are valid, proceed with saving the data
            if (isValid) {
                savePersonalInfo(name, nickname, hometown, age, gender, color, comfort, food, hobbies, sports)
                Toast.makeText(this, "Data submitted successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener {
            val back = Intent(this, HomeActivity::class.java)
            startActivity(back)
        }
    }

    private fun savePersonalInfo(
        name: String, nickname: String, hometown: String, age: String, gender: String,
        color: String, comfort: String, food: String, hobbies: String, sports: String
    ) {
        // Retrieve user inputs and current date
        val currentDateTime = getCurrentDateTime()
        val personalInfo = PersonalInfo(
            name = name,
            nickname = nickname,
            hometown = hometown,
            age = age,
            gender = gender,
            color = color,
            comfort = comfort,
            food = food,
            hobbies = hobbies,
            sports = sports,
            dateAdded = currentDateTime
        )

        // Load existing list from SharedPreferences
        val sharedPreferences = getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonString = sharedPreferences.getString("PersonalInfoList", null)
        val type = object : TypeToken<MutableList<PersonalInfo>>() {}.type
        val personalInfoList: MutableList<PersonalInfo> = gson.fromJson(jsonString, type) ?: mutableListOf()

        // Add the new personalInfo to the list
        personalInfoList.add(personalInfo)

        // Save the updated list back to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("PersonalInfoList", gson.toJson(personalInfoList))
        editor.apply()

        // Provide feedback to the user
        showToast("Data saved successfully! Added on: $currentDateTime")

        // Return result to HomeActivity
        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)

        // Finish the activity and return to HomeActivity
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}
