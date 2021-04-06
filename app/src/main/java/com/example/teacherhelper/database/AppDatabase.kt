package com.example.teacherhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Groups::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val  groupsDAO : GroupsDAO

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Groups ADD COLUMN hours INTEGER DEFAULT 0 NOT NULL")
            }
        }
        @Volatile
        private var INSTANCE:AppDatabase? = null
        fun getInstance(context: Context):AppDatabase{
            synchronized(this){
                var instance:AppDatabase? = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "groupsDatabase"
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                }
                return instance
            }
        }
    }
}