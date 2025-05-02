package com.example.remiderapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.remiderapp.Database.TagDatabase
import com.example.remiderapp.Database.TaskDatabase
import com.example.remiderapp.Model.Tag
import com.example.remiderapp.Model.Task
import com.example.remiderapp.Repository.TagRepository
import com.example.remiderapp.Repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagViewModel(application: Application) : AndroidViewModel(application) {

    private  val _tags : LiveData<List<Tag>>
    private  val _tagRepository : TagRepository
    init {

        val tagDao = TagDatabase.getDatabase(application).tagDAO()
        _tagRepository = TagRepository(tagDao)
        _tags = _tagRepository.tags

    }
    fun addTag(tag: Tag)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _tagRepository.addTag(tag)
            //loadImage()
        }
    }
    fun updateTag(tag: Tag)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _tagRepository.updateTag(tag)
        }
    }
    fun deleteTag(tag: Tag)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _tagRepository.deleteTag(tag)
        }
    }
    fun getTag(): LiveData<List<Tag>>
    {
        return _tags;
    }
}