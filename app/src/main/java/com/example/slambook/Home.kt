package com.example.slambook

import PersonalInfo
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slambook.databinding.ActivityHomeBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val gson = Gson()

    private lateinit var personalInfoList: MutableList<PersonalInfo> // Use MutableList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load saved data
        personalInfoList = loadPersonalInfoList() ?: mutableListOf()

        val adapter = PersonalInfoAdapter(personalInfoList, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.addBtn.setOnClickListener {
            val toNext = Intent(this, SlamFields::class.java)
            startActivityForResult(toNext, 1) // Start activity for result
        }
    }

    private fun loadPersonalInfoList(): MutableList<PersonalInfo> {
        val sharedPreferences = getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonString = sharedPreferences.getString("PersonalInfoList", null)
        val type = object : TypeToken<MutableList<PersonalInfo>>() {}.type
        return gson.fromJson(jsonString, type) ?: mutableListOf()
    }

    override fun onResume() {
        super.onResume()
        // Load data from SharedPreferences
        val personalInfoList = loadPersonalInfoList()

        // Set up RecyclerView with adapter
        val adapter = PersonalInfoAdapter(personalInfoList, this) // Pass 'this' as context (HomeActivity context)
        binding.recyclerView.adapter = adapter
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            // Safely retrieve the updated PersonalInfo object
            val updatedFriend: PersonalInfo? = data.getParcelableExtra("update_friend")

            if (updatedFriend != null) {
                // Load the existing list of PersonalInfo objects
                val sharedPreferences = getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
                val gson = Gson()
                val jsonString = sharedPreferences.getString("PersonalInfoList", null)
                val type = object : TypeToken<MutableList<PersonalInfo>>() {}.type
                val personalInfoList: MutableList<PersonalInfo> = gson.fromJson(jsonString, type) ?: mutableListOf()

                // Find and update the modified item in the list
                val index = personalInfoList.indexOfFirst { it.name == updatedFriend.name }
                if (index != -1) {
                    personalInfoList[index] = updatedFriend
                }

                // Save the updated list back to SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("PersonalInfoList", gson.toJson(personalInfoList))
                editor.apply()

                // Refresh the RecyclerView to display the updated list
                val adapter = PersonalInfoAdapter(personalInfoList, this)
                binding.recyclerView.adapter = adapter
            } else {
                // Log error if updatedFriend is null
                Log.e("HomeActivity", "Received null update_friend")
            }
        } else {
            // Handle case for unexpected resultCode or data
            Log.e("HomeActivity", "Unexpected result or null data")
        }
    }


}
