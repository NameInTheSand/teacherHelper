package com.example.teacherhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Groups::class, Student::class, Thems::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract val  groupsDAO : GroupsDAO

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Groups ADD COLUMN hours INTEGER DEFAULT 0 NOT NULL")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Students` (`id` INTEGER DEFAULT 0 NOT NULL, `name` TEXT NOT NULL, `groupName` TEXT NOT NULL,  `surname` TEXT NOT NULL, PRIMARY KEY(`id`))")
            }
        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Thems` (`id` INTEGER DEFAULT 0 NOT NULL, `mark` INTEGER NOT NULL, `name` TEXT NOT NULL, `groupName` TEXT NOT NULL,  `maxMark` INTEGER NOT NULL, PRIMARY KEY(`id`))")
            }
        }
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Thems ADD COLUMN studentId TEXT ")
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
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                        .build()
                }
                return instance
            }
        }
    }
}