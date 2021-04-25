package com.example.teacherhelper.groups

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.database.GruopsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GroupsViewModel(private val groupsRepository: GruopsRepository) : ViewModel(),Observable {

    val groups = groupsRepository.groups

     fun insert(groups:Groups):Job = viewModelScope.launch {
        groupsRepository.insert(groups)
    }
    fun addHour(newHours:Int, searchId:Int):Job = viewModelScope.launch {
        groupsRepository.update(newHours,searchId)
    }

    fun updateGroup(newName: String,newCourse:Int,searchId: Int):Job = viewModelScope.launch {
        groupsRepository.updateGroup(newName,newCourse,searchId)
    }

    fun deleteGroup(id:String) = viewModelScope.launch {
        groupsRepository.delete(id)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}