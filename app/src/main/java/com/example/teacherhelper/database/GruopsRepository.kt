package com.example.teacherhelper.database

import androidx.lifecycle.LiveData

class GruopsRepository(private val dao: GroupsDAO) {
    val groups = dao.getAllGroups()

    suspend fun insert(groups: Groups){
            dao.insert(groups)
    }
    suspend fun delete(groups: Groups){
        dao.deleteGroup(groups)
    }
    suspend fun update(newHours:Int, searchId:Int){
        dao.update(newHours,searchId)
    }
    suspend fun nukeTable(){
        dao.nukeTable()
    }
    suspend fun search(group: String) : List<Groups>{
       return dao.getSearch(group)
    }
}