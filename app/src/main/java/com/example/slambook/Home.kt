package com.example.slambook

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        binding.addBtn.setOnClickListener{
            val toNext = Intent(this, SlamFields::class.java)
            startActivityForResult(toNext, 1) // Start activity for result
        }

        // Set up RecyclerView
        val adapter = PersonalInfoAdapter(personalInfoList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadPersonalInfoList(): MutableList<PersonalInfo>? {
        val sharedPreferences = getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("PersonalInfoList", null)

        // Explicitly specify the type using TypeToken
        val type = object : TypeToken<MutableList<PersonalInfo>>() {}.type

        // Convert the JSON string into a MutableList of PersonalInfo objects
        return gson.fromJson<MutableList<PersonalInfo>>(jsonString, type)
    }


    // Handle result from SlamFields Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Reload data after returning from SlamFields
            personalInfoList.clear()
            personalInfoList.addAll(loadPersonalInfoList() ?: mutableListOf())
            binding.recyclerView.adapter?.notifyDataSetChanged() // Notify adapter of data change
        }
    }
}
