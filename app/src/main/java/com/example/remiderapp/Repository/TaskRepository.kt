package com.example.remiderapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.remiderapp.DAO.TaskDAO
import com.example.remiderapp.Model.Task

class TaskRepository(private val taskDAO: TaskDAO) {
    val images : LiveData<List<Task>> = taskDAO.getAllTask()
    suspend fun addTask(task:Task)
    {
        Log.d("Test2","image:${task}")
        taskDAO.insertTask(task)
    }
    suspend fun  updateTask(task:Task)
    {
        taskDAO.updateTask(task)
    }
    suspend fun  deleteTask(task:Task)
    {
        taskDAO.deleteTask(task)
    }
    suspend fun findTaskName(name:String):Task?
    {
        return taskDAO.findTaskName(name)
    }
    suspend fun findTaskID(id:Int):Task?
    {
        return taskDAO.findTaskID(id)
    }
    suspend fun findTaskDay(day:String):Task?
    {
        return taskDAO.findTaskDay(day)
    }
//    suspend fun  findImage(uri : String): Task?
//    {
//        return taskDAO.findImage(uri)
//    }
//    suspend fun findImageNext(id : Int): Task?
//    {
//        return  taskDAO.findImageNext(id)
//    }
//    suspend fun findImagePrevious(id : Int): Task?
//    {
//        return  taskDAO.findImagePrevious(id)
//    }
}