package com.example.teacherhelper.database

class GruopsRepository(private val dao: GroupsDAO) {
    val groups = dao.getAllGroups()
    val students = dao.getGroupStudentsAll()
    val thems = dao.getThems()

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

    suspend fun insertStudent(student: Student){
        dao.insertStudent(student)
    }

    suspend fun insertThemes(thems: Thems){
        dao.insertTheme(thems)
    }

     suspend fun getGroupStudents(id: String): List<Student> {
        return dao.getGroupStudentsFromGroup(id)
    }

    suspend fun getGroupThems(id: String): List<Thems> {
        return dao.getGroupThems(id)
    }

    suspend fun getGroupThemsFroStudent(search: String?, studentId: String?) : List<Thems>{
            return dao.getGroupThemsFroStudent(search,studentId)
    }
}