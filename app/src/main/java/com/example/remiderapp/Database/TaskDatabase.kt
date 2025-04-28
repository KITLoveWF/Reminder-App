package com.example.remiderapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remiderapp.DAO.TaskDAO
import com.example.remiderapp.Model.Task

@Database(entities = [Task::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    abstract  fun taskDAO (): TaskDAO
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            val temInstance = INSTANCE
            if(temInstance!=null)
            {
                return  temInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "image_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}