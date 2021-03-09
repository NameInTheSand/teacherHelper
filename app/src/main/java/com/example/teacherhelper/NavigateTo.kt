package com.example.teacherhelper

interface Navigable {
    fun navigateTo(id: Int, popToStart: Boolean = false)
}