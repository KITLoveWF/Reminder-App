package com.example.remiderapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remiderapp.DAO.TagDAO
import com.example.remiderapp.DAO.TaskDAO
import com.example.remiderapp.Model.Tag
import com.example.remiderapp.Model.Task

@Database(entities = [Tag::class], version = 2)
abstract class TagDatabase : RoomDatabase()  {
    abstract  fun tagDAO (): TagDAO
    companion object {
        @Volatile
        private var INSTANCE: TagDatabase? = null

        fun getDatabase(context: Context): TagDatabase {
            val temInstance = INSTANCE
            if(temInstance!=null)
            {
                return  temInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TagDatabase::class.java,
                    "Reminder_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}