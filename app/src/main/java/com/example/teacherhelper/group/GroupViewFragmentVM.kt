package com.example.teacherhelper.group

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.database.Student
import com.example.teacherhelper.database.Thems
import com.example.teacherhelper.utils.ioContext
import com.example.teacherhelper.utils.sendValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GroupViewFragmentVM(private val groupsRepository: GruopsRepository) :
    ViewModel(), Observable {
    val students = groupsRepository.students
    val thems = groupsRepository.thems

    fun insertStudent(student: Student): Job = viewModelScope.launch {
        groupsRepository.insertStudent(student)
    }

    fun insertTheme(thems: Thems): Job = viewModelScope.launch {
        groupsRepository.insertThemes(thems)
    }
    fun deleteStudents():Job = viewModelScope.launch {
        groupsRepository.deleteStudents()
    }

    fun deleteThems():Job = viewModelScope.launch {
        groupsRepository.deleteThems()
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}