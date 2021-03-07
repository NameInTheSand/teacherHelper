package com.example.teacherhelper.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Groups::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupsDAO(): GroupsDAO
}