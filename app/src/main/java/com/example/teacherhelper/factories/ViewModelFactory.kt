package com.example.teacherhelper.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.group.GroupViewFragmentVM
import com.example.teacherhelper.groups.GroupsViewModel

class ViewModelFactory(private val groupsRepository: GruopsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupsViewModel::class.java)){
            return GroupsViewModel(groupsRepository)as T
        }
        if(modelClass.isAssignableFrom(GroupViewFragmentVM::class.java)){
            return GroupViewFragmentVM(groupsRepository)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}