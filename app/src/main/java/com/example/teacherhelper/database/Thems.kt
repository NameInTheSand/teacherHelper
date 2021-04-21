package com.example.teacherhelper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thems")
data class Thems(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mark: Int,
    val name: String,
    val maxMark: Int,
    val groupName: String,
    val studentId:String?,
)
