package com.example.teacherhelper.groups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.databinding.ItemGroupBinding

class GroupsAdapter(private val allGroups: List<Groups>) :
    RecyclerView.Adapter<GroupsAdapter.groupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): groupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemGroupBinding = inflate(layoutInflater, R.layout.item_group, parent, false)
        return groupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: groupViewHolder, position: Int) {
        holder.bind(allGroups[position])
    }

    override fun getItemCount(): Int {
        return allGroups.size
    }

    class groupViewHolder(val binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(groups: Groups) {
            binding.groupName.text = groups.name
            binding.groupCourse.text = groups.course.toString()
        }
    }


}
