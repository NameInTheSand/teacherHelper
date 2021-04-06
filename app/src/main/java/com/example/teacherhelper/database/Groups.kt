package com.example.teacherhelper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Groups(
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val name:String,
    val course:Int,
    val hours:Int=0
)
