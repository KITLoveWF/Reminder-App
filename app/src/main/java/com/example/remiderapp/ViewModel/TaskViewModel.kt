package com.example.remiderapp.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.remiderapp.Database.TaskDatabase
import com.example.remiderapp.Model.Task
import com.example.remiderapp.Repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {


    private  val _tasks : LiveData<List<Task>>
    private  val _taskRepository : TaskRepository
    init {

        val taskDao = TaskDatabase.getDatabase(application).taskDAO()
        _taskRepository = TaskRepository(taskDao)
        _tasks = _taskRepository.tasks

    }
    fun addTask(task: Task)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _taskRepository.addTask(task)
            //loadImage()
        }
    }
    fun updateTask(task:Task)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _taskRepository.updateTask(task)
        }
    }
    fun deleteTask(task:Task)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _taskRepository.deleteTask(task)
        }
    }
    fun getTask(): LiveData<List<Task>>
    {
        return _tasks;
    }
    suspend fun findTaskName(name: String): Task? {

        return _taskRepository.findTaskName(name)
    }
    suspend fun findTaskID(id: Int): Task? {

        return _taskRepository.findTaskID(id)
    }
    suspend fun findTaskDay(day: String): Task? {

        return _taskRepository.findTaskDay(day)
    }
//    suspend fun getNextImageUri(id : Int): Image? {
//        return _imageRepository.findImageNext(id)
//    }
//
//    suspend fun getPreviousImageUri(id: Int): Image? {
//        return _imageRepository.findImagePrevious(id)
//    }
}