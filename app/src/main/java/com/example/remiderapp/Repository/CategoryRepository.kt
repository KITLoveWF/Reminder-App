package com.example.remiderapp.Repository

import androidx.lifecycle.LiveData
import com.example.remiderapp.DAO.CategoryDAO
import com.example.remiderapp.Model.Category

class CategoryRepository(private val categoryDAO: CategoryDAO) {
    val categorys : LiveData<List<Category>> = categoryDAO.getAllCategory()
    suspend fun addCategory(category: Category)
    {
        //Log.d("Test2","image:${task}")
        categoryDAO.insertCategory(category)
    }
    suspend fun  updateCategory(category: Category)
    {
        categoryDAO.updateCategory(category)
    }
    suspend fun  deleteCategory(category: Category)
    {
        categoryDAO.deleteCategory(category)
    }
}