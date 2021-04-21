package com.example.teacherhelper.group

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.database.Student
import com.example.teacherhelper.utils.ioContext
import com.example.teacherhelper.utils.sendValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GroupViewFragmentVM(private val groupsRepository: GruopsRepository) :
    ViewModel(), Observable {

    val _studentsList = MutableLiveData<List<StudentModel>>()
    val studentList: LiveData<List<StudentModel>> = _studentsList

    fun insert(student: Student): Job = viewModelScope.launch {
        groupsRepository.insertStudent(student)
    }
    fun getStudents(id: String) {

        var list:MutableList<StudentModel> = mutableListOf()
        viewModelScope.launch {
                groupsRepository.getGroupStudents(id).let {
                    it.forEach {
                        list.add(StudentModel(it,"10/10"))
                    }
                    _studentsList.sendValue(list)
                }
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}