package com.example.remiderapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val time: String,
    val day: String,
    val reminder:Boolean,
    val priority: Int? = null,
    val complete: Boolean,
    val categoryId:Int? = null
)
