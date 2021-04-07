package com.example.teacherhelper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val groupName: String,
    val name: String,
    val surname: String
)
