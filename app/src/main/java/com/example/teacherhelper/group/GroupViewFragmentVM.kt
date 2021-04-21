package com.example.teacherhelper.group

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.utils.ioContext
import com.example.teacherhelper.utils.sendValue
import kotlinx.coroutines.launch

class GroupViewFragmentVM(private val groupsRepository: GruopsRepository) :
    ViewModel(), Observable {

    val _studentsList = MutableLiveData<StudentModel>()
    val studentList: LiveData<StudentModel> = _studentsList


    fun getStudents(id: String) {

        viewModelScope.launch {
            ioContext {
                groupsRepository.getGroupStudents(id)
            }.map {
                _studentsList.sendValue(StudentModel(it, "10/10"))
            }
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}