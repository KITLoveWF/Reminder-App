package com.example.remiderapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.remiderapp.Database.AppDatabase
import com.example.remiderapp.Model.Category
import com.example.remiderapp.Repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private  val _categorys : LiveData<List<Category>>
    private  val _categoryRepository : CategoryRepository
    init {

        val categoryDao = AppDatabase.getDatabase(application).categoryDAO()
        _categoryRepository = CategoryRepository(categoryDao)
        _categorys = _categoryRepository.categorys

    }
    fun addCategory(category: Category)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _categoryRepository.addCategory(category)
            //loadImage()
        }
    }
    fun updateCategory(category: Category)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _categoryRepository.updateCategory(category)
        }
    }
    fun deleteCategory(category: Category)
    {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d("Test1","image:${image}")
            _categoryRepository.deleteCategory(category)
        }
    }
    fun getCategory(): LiveData<List<Category>>
    {
        return _categorys;
    }
}