package com.example.slambook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slambook.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from SharedPreferences
        val sharedPreferences = getSharedPreferences("SlamBookPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("NAME", null)
        val nickname = sharedPreferences.getString("NICKNAME", null)
        val age = sharedPreferences.getInt("AGE", -1) // Use -1 as a marker for no data
        val gender = sharedPreferences.getString("GENDER", null)
        val hometown = sharedPreferences.getString("HOMETOWN", null)
        val color = sharedPreferences.getString("COLOR", null)
        val food = sharedPreferences.getString("FOOD", null)
        val comfortfood = sharedPreferences.getString("COMFORT FOOD", null)
        val place = sharedPreferences.getString("PLACE", null)
        val hobbies = sharedPreferences.getString("HOBBIES", null)
        val sports = sharedPreferences.getString("SPORTS", null)
        val currentDate = sharedPreferences.getString("DATE", null)

        // Check if any data is available
        val isDataAvailable = name != null || nickname != null || age != -1 || gender != null || hometown != null || color != null || food != null || comfortfood != null || place != null || hobbies != null || sports != null

        // Handle empty data state
        if (isDataAvailable) {
            // Prepare data for RecyclerView using SlamBookEntry
            val dataList = mutableListOf<SlamBookEntry>()

            // Add each non-empty field to the data list
            if (!name.isNullOrEmpty()) dataList.add(SlamBookEntry("Name: $name", currentDate ?: ""))
            if (!nickname.isNullOrEmpty()) dataList.add(SlamBookEntry("Nickname: $nickname", currentDate ?: ""))
            if (age != -1) dataList.add(SlamBookEntry("Age: $age", currentDate ?: ""))
            if (!gender.isNullOrEmpty()) dataList.add(SlamBookEntry("Gender: $gender", currentDate ?: ""))
            if (!hometown.isNullOrEmpty()) dataList.add(SlamBookEntry("Hometown: $hometown", currentDate ?: ""))
            if (!color.isNullOrEmpty()) dataList.add(SlamBookEntry("Color: $color", currentDate ?: ""))
            if (!food.isNullOrEmpty()) dataList.add(SlamBookEntry("Food: $food", currentDate ?: ""))
            if (!comfortfood.isNullOrEmpty()) dataList.add(SlamBookEntry("Comfort Food: $comfortfood", currentDate ?: ""))
            if (!place.isNullOrEmpty()) dataList.add(SlamBookEntry("Place: $place", currentDate ?: ""))
            if (!hobbies.isNullOrEmpty()) dataList.add(SlamBookEntry("Hobbies: $hobbies", currentDate ?: ""))
            if (!sports.isNullOrEmpty()) dataList.add(SlamBookEntry("Sports: $sports", currentDate ?: ""))

            // Show RecyclerView and hide empty placeholder
            binding.emptyTextViewHolder.visibility = View.GONE // Hide the empty placeholder
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = LayoutAdapter(dataList) // Set the adapter for RecyclerView
        } else {
            // Show empty placeholder and hide RecyclerView
            binding.emptyTextViewHolder.visibility = View.VISIBLE // Show the empty placeholder
            binding.recyclerView.visibility = View.GONE // Hide RecyclerView
        }

        // Add button click to open the fill form
        binding.addBtn.setOnClickListener {
            val intent = Intent(this, SlamFill1::class.java)
            startActivity(intent)
        }
    }

    // Function to get the current date
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date()) // Get current date
    }
}
