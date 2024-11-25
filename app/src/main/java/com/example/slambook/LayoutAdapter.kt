// LayoutAdapter.kt
package com.example.slambook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LayoutAdapter(private val data: List<SlamBookEntry>) : RecyclerView.Adapter<LayoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the custom layout for each item in the list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_name, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the SlamBookEntry for the current position
        val entry = data[position]

        // Set the name and current date into the corresponding TextViews
        holder.nameTextView.text = entry.name
        holder.dateTextView.text = entry.date
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // ViewHolder class to hold the references to the UI elements
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val dateTextView: TextView = itemView.findViewById(R.id.item_date)
    }
}
