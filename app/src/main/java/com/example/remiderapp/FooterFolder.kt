package com.example.remiderapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remiderapp.Model.Category
import com.example.remiderapp.ViewModel.CategoryViewModel

@Composable
fun FooterFolder(selectedIndex: SnapshotStateList<Category>)
{
    var isOpenUpdate by remember{ mutableStateOf(false) }
    var indexedit by remember { mutableStateOf(-1) }
    var contentOld by remember { mutableStateOf("") }
    var contentNew by remember { mutableStateOf("") }
    val categoryViewModel: CategoryViewModel = viewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = colorResource(id = R.color.black))
    )
    {
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    isOpenUpdate=!isOpenUpdate
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, // Màu nền button
                    contentColor = Color.White // Màu chữ (content) trên button
                )

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa nội dung
                    verticalArrangement = Arrangement.Center // Căn giữa dọc
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit), // replace with your icon
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Chỉnh sửa")
                }
            }

        }
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {

                    val toDelete = selectedIndex.toList() // tránh lỗi khi xóa trong khi đang lặp
                    toDelete.forEach { category ->
                        categoryViewModel.deleteCategory(category)
                        selectedIndex.remove(category)
                    }
//                    for (index in selectedIndex.sortedDescending()) {
//                        if (index in folders.indices) {
//                            folders.removeAt(index)
//                        }
//                    }
                    //folders.forEachIndexed { index,(name,count) ->selectedIndex.remove(index)}
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, // Màu nền button
                    contentColor = Color.White // Màu chữ (content) trên button
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa nội dung
                    verticalArrangement = Arrangement.Center // Căn giữa dọc
                )
                {

                    Icon(
                        painter = painterResource(id = R.drawable.trash), // replace with your icon
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Xóa")
                }
            }

        }
    }
    if (selectedIndex.isNotEmpty()) {
        UpdateFolder(
            onDimiss = {
                isOpenUpdate = false
            },
            Save ={ it -> contentOld = it },
            isOpenUpdate,
            selectedIndex[0]
        )
    }
}