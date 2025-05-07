package com.example.remiderapp

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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ViewModel.CategoryViewModel
import com.example.remiderapp.ViewModel.TaskViewModel

@Composable
fun FooterTask(
    completeTask: List<Task>,
    onDimiss:()->Unit
)
{
    val taskViewModel: TaskViewModel = viewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = colorResource(id = R.color.black))
    )
    {
//        Column(modifier = Modifier
//            .weight(1f)
//            .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                onClick = {
//
//                    onDimiss()
//                          },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black, // Màu nền button
//                    contentColor = Color.White // Màu chữ (content) trên button
//                )
//
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa nội dung
//                    verticalArrangement = Arrangement.Center // Căn giữa dọc
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.send), // replace with your icon
//                        contentDescription = "Button Icon",
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Text(text = "Chuyển tới")
//                }
//            }
//
//        }
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    completeTask.forEach { task ->
                        taskViewModel.deleteTask(task)
                        //completeTask.remove(task)
                    }
                    onDimiss()

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
}