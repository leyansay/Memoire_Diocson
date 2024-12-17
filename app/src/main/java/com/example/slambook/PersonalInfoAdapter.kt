package com.example.slambook

import PersonalInfo
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.slambook.databinding.ListNameBinding
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

class PersonalInfoAdapter(
    private val personalInfoList: MutableList<PersonalInfo>, // MutableList for updating data
    private val context: Context
) : RecyclerView.Adapter<PersonalInfoAdapter.PersonalInfoViewHolder>() {

    inner class PersonalInfoViewHolder(val binding: ListNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Get the selected item
                    val personalInfo = personalInfoList[position]
                    // Start the activity with the clicked item
                    navigateToDetails(personalInfo)
                }
            }

            // Set up delete button click listener
            binding.deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Show confirmation dialog before deleting
                    confirmDelete(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalInfoViewHolder {
        val binding = ListNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PersonalInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonalInfoViewHolder, position: Int) {
        val personalInfo = personalInfoList[position]

        // Populate the views with data
        with(holder.binding) {
            itemName.text = personalInfo.name
            itemDate.text = personalInfo.dateAdded
        }
    }

    override fun getItemCount(): Int = personalInfoList.size

    private fun navigateToDetails(personalInfo: PersonalInfo) {
        val intent = Intent(context, FriendsInfo::class.java).apply {
            putExtra("personal_info", personalInfo) // Pass the PersonalInfo object
        }
        context.startActivity(intent)
    }

    private fun confirmDelete(position: Int) {
        // Show a confirmation dialog before deleting
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Confirmation")
            .setMessage("Are you sure you want to delete this friend's information?")
            .setPositiveButton("Yes") { _, _ -> deleteFriend(position) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteFriend(position: Int) {
        // Remove the item from the list
        personalInfoList.removeAt(position)

        // Update shared preferences
        savePersonalInfoList(personalInfoList)

        // Notify the adapter of the change
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, personalInfoList.size)

        // Show a confirmation message
        Toast.makeText(context, "Friend's information deleted successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun savePersonalInfoList(list: MutableList<PersonalInfo>) {
        val sharedPreferences = context.getSharedPreferences("SlamFieldsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val jsonString = Gson().toJson(list) // Convert the list to JSON
        editor.putString("PersonalInfoList", jsonString)
        editor.apply() // Don't forget to apply changes
    }

}
