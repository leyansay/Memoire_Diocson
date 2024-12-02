package com.example.slambook

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

data class PersonalInfo(
    val name: String,
    val nickname: String,
    val hometown: String,
    val age: String,
    val gender: String,
    val color: String,
    val food: String,
    val place: String,
    val hobbies: String,
    val sports: String,
    val dateAdded: String // Added field for date
)

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

        // Set Done button click listener
        binding.buttonDone.setOnClickListener {
            savePersonalInfo()
        }

        binding.back.setOnClickListener{
            val back = Intent(this, HomeActivity::class.java)
            startActivity(back)
        }
    }

    private fun savePersonalInfo() {
        // Retrieve user inputs
        val currentDateTime = getCurrentDateTime()
        val personalInfo = PersonalInfo(
            name = binding.name.text.toString(),
            nickname = binding.editTextText5.text.toString(),
            hometown = binding.editTextHometown.text.toString(),
            age = binding.editTextAge.text.toString(),
            gender = binding.spinnergender.selectedItem.toString(),
            color = binding.editTextColor.text.toString(),
            food = binding.editTextFood.text.toString(),
            place = binding.editTextPlace.text.toString(),
            hobbies = binding.editTextHobbies.text.toString(),
            sports = binding.editTextSports.text.toString(),
            dateAdded = currentDateTime
        )

        // Load existing list
        val sharedPreferences = getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonString = sharedPreferences.getString("PersonalInfoList", null)
        val type = object : TypeToken<List<PersonalInfo>>() {}.type
        val personalInfoList: MutableList<PersonalInfo> = gson.fromJson(jsonString, type) ?: mutableListOf()

        // Add new entry
        personalInfoList.add(personalInfo)

        // Save updated list
        val editor = sharedPreferences.edit()
        editor.putString("PersonalInfoList", gson.toJson(personalInfoList))
        editor.apply()

        // Return result back to HomeActivity
        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)

        // Feedback
        showToast("Data saved successfully! Added on: $currentDateTime")

        // Finish the activity
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Helper function to get the current date and time
    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
