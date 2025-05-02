package com.example.remiderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
@Preview(showBackground = true)

fun ListTask()
{
    //navController: NavController
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),

    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()

        ) {

        }
        // Phần trên
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()

            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Danh sách nhiệm vụ", style = TextStyle(
                fontSize = 20.sp,
                color = Color.White
            )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TaskRow()
            Spacer(modifier = Modifier.height(16.dp))
            TaskRow()
            Spacer(modifier = Modifier.height(16.dp))
            TaskRow()

        }

        // Phần dưới
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Đã hoàn thành", style = TextStyle(
                fontSize = 20.sp,
                color = Color.White
            )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TaskRow()
            Spacer(modifier = Modifier.height(16.dp))
            TaskRow()
            Spacer(modifier = Modifier.height(16.dp))
            TaskRow()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Footer()
        }
    }




}