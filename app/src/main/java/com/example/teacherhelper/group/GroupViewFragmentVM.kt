package com.example.teacherhelper.group

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.example.teacherhelper.database.GruopsRepository

class GroupViewFragmentVM(private val groupsRepository: GruopsRepository): ViewModel(), Observable {
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}