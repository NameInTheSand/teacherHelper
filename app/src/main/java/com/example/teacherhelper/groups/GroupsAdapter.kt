package com.example.teacherhelper.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.R
import com.example.teacherhelper.database.Groups

class GroupsAdapter(private val allGroups: List<Groups>) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = allGroups[position].name
        holder.course.text = allGroups[position].course.toString()
    }


    override fun getItemCount() = allGroups.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.group_name)
        val course: TextView = view.findViewById(R.id.group_course)
    }


}
