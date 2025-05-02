package com.example.remiderapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.remiderapp.DAO.TagDAO
import com.example.remiderapp.DAO.TaskDAO
import com.example.remiderapp.Model.Tag
import com.example.remiderapp.Model.Task

class TagRepository(private val tagDAO: TagDAO) {
    val tags : LiveData<List<Tag>> = tagDAO.getAllTag()
    suspend fun addTag(tag: Tag)
    {
        //Log.d("Test2","image:${task}")
        tagDAO.insertTag(tag)
    }
    suspend fun  updateTag(tag: Tag)
    {
        tagDAO.updateTag(tag)
    }
    suspend fun  deleteTag(tag: Tag)
    {
        tagDAO.deleteTag(tag)
    }
}