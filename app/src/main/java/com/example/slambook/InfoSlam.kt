package com.example.slambook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivityInfoSlamBinding

class InfoSlam : AppCompatActivity() {

    private lateinit var binding: ActivityInfoSlamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSlamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the PersonalInfo object passed via Intent

        }
    }

