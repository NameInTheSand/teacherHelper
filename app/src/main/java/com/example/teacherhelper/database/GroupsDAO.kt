package com.example.teacherhelper.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroupsDAO {
    @Query("SELECT * from groups")
    fun getAllGroups(): LiveData<List<Groups>>

    @Query("SELECT hours from groups")
    fun getHours(): LiveData<Int>

    @Query("SELECT * from groups WHERE name LIKE :search ")
    suspend fun getSearch(search: String?): List<Groups>

    @Query("UPDATE groups set hours =:newHours WHERE id LIKE :searchId")
    suspend fun update(newHours:Int, searchId:Int)

    @Query("DELETE FROM groups")
    suspend fun nukeTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(groups: Groups)

    @Delete()
    suspend fun deleteGroup(groups: Groups)

}