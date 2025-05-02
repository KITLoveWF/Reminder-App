package com.example.remiderapp.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.remiderapp.Model.Task

@Dao
interface TaskDAO {

    @Query("select * from Task")
    fun getAllTask(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task:Task)

    @Update
    suspend fun updateTask(task:Task)

    @Delete
    suspend fun deleteTask(task:Task)

    @Query("SELECT * FROM Task WHERE title = :name")
    suspend fun findTaskName(name:String) : Task?

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun findTaskID(id:Int) : Task?
    @Query("SELECT * FROM Task WHERE day = :day")
    suspend fun findTaskDay(day:String) : Task?

//    @Query("SELECT * FROM Image WHERE uri = :url")
//    suspend fun findImage(url:String) : Image?

//    @Query("SELECT * FROM Task WHERE  id = (:id - 1) ")
//    suspend fun findImagePrevious(id : Int) : Task?
//
//    @Query("SELECT * FROM Task WHERE  id = (:id + 1) ")
//    suspend fun findImageNext(id : Int) : Task?
}