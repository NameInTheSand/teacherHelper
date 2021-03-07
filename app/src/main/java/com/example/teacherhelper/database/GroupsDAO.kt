package com.example.teacherhelper.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GroupsDAO {
    @Query("SELECT * from groups")
    fun getAllGroups(): List<Groups>

    @Query("SELECT * from groups WHERE course LIKE :search ")
    fun getSearch(search: String?): List<Groups>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(groups: Groups)
}