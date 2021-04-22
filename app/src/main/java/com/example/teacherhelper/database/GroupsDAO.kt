package com.example.teacherhelper.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GroupsDAO {
    @Query("SELECT * from groups")
    fun getAllGroups(): LiveData<List<Groups>>

    @Query("SELECT hours from groups")
    fun getHours(): LiveData<Int>

    @Query("SELECT * from groups WHERE name LIKE :search ")
    suspend fun getSearch(search: String?): List<Groups>

    @Query("UPDATE groups set hours =:newHours WHERE id LIKE :searchId")
    suspend fun update(newHours: Int, searchId: Int)

    @Query("DELETE FROM groups WHERE id LIKE :searchId ")
    suspend fun deleteGroup(searchId: String?)

    @Query("DELETE FROM groups")
    suspend fun nukeTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(groups: Groups)

    @Query("SELECT * from students WHERE groupName LIKE :search ")
    suspend fun getGroupStudentsFromGroup(search: String?): List<Student>

    @Query("SELECT * from students")
    fun getGroupStudentsAll(): LiveData<List<Student>>

    @Query("SELECT * from Thems WHERE groupName LIKE :search ")
    suspend fun getGroupThems(search: String?): List<Thems>

    @Query("SELECT * from Thems WHERE groupName LIKE :search AND studentId LIKE :studentId ")
    suspend fun getGroupThemsFroStudent(search: String?, studentId: String?): List<Thems>

    @Query("SELECT * from Thems")
    fun getThems(): LiveData<List<Thems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Query("DELETE FROM groups WHERE id LIKE :searchId ")
    suspend fun deleteStudent(searchId: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(thems: Thems)

    @Query("UPDATE students set name =:newName , surname=:newSurname WHERE id LIKE :searchId")
    suspend fun updateStudent(newName: String, newSurname:String, searchId: Int)

    @Query("DELETE FROM groups WHERE id LIKE :searchId ")
    suspend fun deleteTheme(searchId: String?)

    @Query("UPDATE Thems set maxMark =:newMaxMark, name=:newName WHERE id LIKE :searchId")
    suspend fun updateTheme(newMaxMark: Int, newName: String, searchId: Int)

    @Query("UPDATE Thems set mark =:newMark WHERE id LIKE :searchId")
    suspend fun updateMark(newMark: Int, searchId: Int)
}