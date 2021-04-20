package com.example.teacherhelper.database

class GruopsRepository(private val dao: GroupsDAO) {
    val groups = dao.getAllGroups()

    suspend fun insert(groups: Groups) {
        dao.insert(groups)
    }

    suspend fun delete(id: String) {
        dao.deleteGroup(id)
    }

    suspend fun update(newHours: Int, searchId: Int) {
        dao.update(newHours, searchId)
    }

    suspend fun nukeTable() {
        dao.nukeTable()
    }

    suspend fun search(group: String): List<Groups> {
        return dao.getSearch(group)
    }

    suspend fun getHours() {
        dao.getHours()
    }

    suspend fun getGroupStudents(id: String) {
        dao.getGroupStudents(id)
    }

    suspend fun getGroupThems(id: String) {
        dao.getGroupThems(id)
    }
}