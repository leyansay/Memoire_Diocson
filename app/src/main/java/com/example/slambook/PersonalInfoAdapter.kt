package com.example.slambook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.slambook.databinding.ListNameBinding


class PersonalInfoAdapter(private val personalInfoList: List<PersonalInfo>) :
    RecyclerView.Adapter<PersonalInfoAdapter.PersonalInfoViewHolder>() {

    inner class PersonalInfoViewHolder(val binding: ListNameBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        holder.binding.itemName.text = personalInfo.name
        holder.binding.itemDate.text = personalInfo.dateAdded
    }

    override fun getItemCount(): Int = personalInfoList.size
}
