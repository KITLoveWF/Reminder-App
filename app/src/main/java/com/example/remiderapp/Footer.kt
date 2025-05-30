package com.example.remiderapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
//@Preview(showBackground = true)
fun Footer(navController: NavController)
{
    //navController: NavController
    //navController.navigate("page1")
    //navController.navigate("page2")
    //navController.navigate("page3")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = colorResource(id = R.color.black))
//            .padding(bottom = 10.dp)
           // .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    )
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {navController.navigate("page1")},
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
                        painter = painterResource(id = R.drawable.home), // replace with your icon
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(16.dp),
                    )
                    Text(text = "Trang chủ")
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
                onClick = {navController.navigate("page2")},
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
                        painter = painterResource(id = R.drawable.calendar), // replace with your icon
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Tháng")
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
                onClick = {navController.navigate("page3")},
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
                        painter = painterResource(id = R.drawable.clock), // replace with your icon
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Tuần")
                }
            }

        }
    }
}