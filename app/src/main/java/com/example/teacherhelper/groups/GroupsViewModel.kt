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

    var searchResult:List<Groups> = mutableListOf()

     fun insert(groups:Groups):Job = viewModelScope.launch {
        groupsRepository.insert(groups)
    }
    fun search(group: String?) {
        if(group.isNullOrEmpty()){
                searchResult = groupsRepository.groups.value!!
        }
        else{
        viewModelScope.launch {
            groupsRepository.search('%'+group+'%').let {
                searchResult = it
            }
        }
        }
    }

    fun nuketable() = viewModelScope.launch {
        groupsRepository.nukeTable()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}