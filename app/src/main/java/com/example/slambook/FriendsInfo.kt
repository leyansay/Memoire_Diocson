package com.example.slambook

import PersonalInfo
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivityFriendsInfoBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FriendsInfo : AppCompatActivity() {
    private lateinit var binding: ActivityFriendsInfoBinding
    private var friend: PersonalInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the PersonalInfo object from the intent
        friend = intent.getParcelableExtra("personal_info")

        // Display the details in the UI using the passed PersonalInfo object
        friend?.let {
            binding.name.setText(it.name)
            binding.nickName.setText(it.nickname)
            binding.homeTown.setText(it.hometown)
            binding.age.setText(it.age)
            binding.gender.setText(it.gender)
            binding.color.setText(it.color)
            binding.comfort.setText(it.comfort?.ifEmpty { "Not specified" })
            binding.food.setText(it.food)
            binding.hobbies.setText(it.hobbies)
            binding.sports.setText(it.sports)
        } ?: run {
            Toast.makeText(this, "No personal information found!", Toast.LENGTH_SHORT).show()
        }

        binding.nameHeader.text = "${friend?.name}'s Slam"

        // Validate before saving
        binding.updateBtn.setOnClickListener {
            // Validate all fields
            if (isFormValid()) {
                saveFriend()
            }
        }

        binding.backBtn.setOnClickListener{
            val backBtn = Intent(this, HomeActivity::class.java)
            startActivity(backBtn)
        }
    }

    // Validate all fields before saving
    private fun isFormValid(): Boolean {
        // Get all field values
        val name = binding.name.text.toString().trim()
        val age = binding.age.text.toString().trim()
        val nickname = binding.nickName.text.toString().trim()
        val hometown = binding.homeTown.text.toString().trim()
        val gender = binding.gender.text.toString().trim()
        val color = binding.color.text.toString().trim()
        val comfort = binding.comfort.text.toString().trim()
        val food = binding.food.text.toString().trim()
        val hobbies = binding.hobbies.text.toString().trim()
        val sports = binding.sports.text.toString().trim()

        // Check if any field is empty
        if (name.isEmpty() || age.isEmpty() || nickname.isEmpty() || hometown.isEmpty() || gender.isEmpty() || color.isEmpty() || comfort.isEmpty()
            || food.isEmpty() || hobbies.isEmpty() || sports.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate age input
        if (age.length != 2) {
            binding.age.error = "Age must be exactly 2 digits" // Show error in the age field
            Toast.makeText(this, "Error: Age must be exactly 2 digits", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun saveFriend() {
        val updatedFriend = PersonalInfo(
            name = binding.name.text.toString(),
            nickname = binding.nickName.text.toString(),
            hometown = binding.homeTown.text.toString(),
            age = binding.age.text.toString(),
            gender = binding.gender.text.toString(),
            color = binding.color.text.toString(),
            food = binding.food.text.toString(),
            comfort = binding.comfort.text.toString(),
            hobbies = binding.hobbies.text.toString(),
            sports = binding.sports.text.toString(),
            dateAdded = friend?.dateAdded ?: "" // Use original date if unchanged
        )

        val result = Intent()
        result.putExtra("update_friend", updatedFriend)
        setResult(Activity.RESULT_OK, result) // Return the updated friend to HomeActivity

        // Save to SharedPreferences
        saveUpdatedPersonalInfo(updatedFriend)

        finish() // Close the activity and return to HomeActivity
    }

    private fun saveUpdatedPersonalInfo(updatedFriend: PersonalInfo) {
        val sharedPreferences = getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonString = sharedPreferences.getString("PersonalInfoList", null)
        val type = object : TypeToken<MutableList<PersonalInfo>>() {}.type
        val personalInfoList: MutableList<PersonalInfo> = gson.fromJson(jsonString, type) ?: mutableListOf()

        // Find and update the existing entry
        val index = personalInfoList.indexOfFirst { it.dateAdded == updatedFriend.dateAdded }
        if (index != -1) {
            personalInfoList[index] = updatedFriend
        }

        // Save the updated list back to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("PersonalInfoList", gson.toJson(personalInfoList))
        editor.apply()

        Toast.makeText(this, "${friend?.name}'s information updated successfully!", Toast.LENGTH_SHORT).show()
    }
}
