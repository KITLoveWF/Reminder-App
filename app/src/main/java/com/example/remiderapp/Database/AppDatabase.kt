package com.example.remiderapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remiderapp.DAO.CategoryDAO
import com.example.remiderapp.DAO.TaskDAO
import com.example.remiderapp.Model.Category
import com.example.remiderapp.Model.Task

@Database(entities = [Task::class, Category::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
    abstract fun categoryDAO(): CategoryDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Reminder_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
