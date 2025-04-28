package com.example.remiderapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.remiderapp.Model.Task

@Composable
//@Preview(showBackground = true)
fun TaskItem(dayText: Int,listTask: MutableList<Task>?,dayNow:Boolean ) {
//    dayText: Int,listTask: MutableList<Task>?,dayNow:Boolean
//    val listTask = mutableListOf(
//        // Ngày 1: 2025-04-23
//        Task(name = "Làm bài tập Toán", content = "Giải 5 bài hình học", time = "08:00", day = "2025-04-23"),
//        Task(name = "Học Tiếng Anh", content = "Ôn từ vựng Unit 5", time = "09:30", day = "2025-04-23"),
//        Task(name = "Họp nhóm", content = "Chuẩn bị bài thuyết trình", time = "14:00", day = "2025-04-23"),
//        Task(name = "Đi siêu thị", content = "Mua đồ ăn cho tuần", time = "17:00", day = "2025-04-23"),
//        Task(name = "Lập kế hoạch tuần", content = "Ghi chú lịch trình công việc", time = "20:00", day = "2025-04-23"),
//    )
//    val dayNow = true
//    val dayText = 1

        val colorList = listOf(
        Color(0xFFBB86FC), // purple_200
        Color(0xFF6200EE), // purple_500
        Color(0xFF3700B3), // purple_700
        Color(0xFF03DAC5), // teal_200
        Color(0xFF018786), // teal_700
        Color(0xFFFF4D4D), // red
        Color(0xFFFF8533)  // orange
    )



    val taskColors = remember {
        List(3) { colorList.random() }
    }
    Box(modifier = Modifier.fillMaxWidth()
        .size(120.dp)
        .padding(2.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White)
    ) {

        // Calendar icon (top right with margin)
//        Image(
//            painter = painterResource(id = R.drawable.calendar),
//            contentDescription = "Calendar Icon",
//            modifier = Modifier
//                .size(48.dp)
//                .padding(top = 50.dp, end = 50.dp),
//            alignment = Alignment.TopEnd,
//
//            contentScale = ContentScale.Fit
//        )
        // Task Name - center
        if(listTask!=null)
        {
            var i = 10
            for ((index,task) in listTask.withIndex())
            {
                if(index<2)
                {
                    i+=30
                    Box(modifier = Modifier.fillMaxWidth() .padding(top = i.dp).clip(RoundedCornerShape(10.dp))) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    taskColors[index]
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        )
                        {
//                            Column(modifier = Modifier.weight(1f).padding(start = 5.dp)) {
//
//
//                            }
                            Text(
                                text = task.name,
                                fontSize = 5.sp,
                                color = Color.White,
                            )

                            Text(
                                text = task.time,
                                fontSize = 5.sp,
                                color = Color.White,

                            )

                        }

                    }

                }
                else if(index==2)
                {
                    i+=30
                   Box(modifier = Modifier.fillMaxWidth() .padding(top = i.dp).clip(RoundedCornerShape(10.dp)))
                   {
                       Row(
                           modifier = Modifier
                               .fillMaxWidth()

                       )
                       {
                           Column(modifier = Modifier
                               .background(taskColors[index])
                               .weight(7f)


                           ) {
                               Row(modifier = Modifier.fillMaxWidth() ,horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically)
                               {
                                   Text(
                                       text = task.name,
                                       fontSize = 5.sp,
                                       color = Color.White,

                                       )
                                   Text(
                                       text = task.time,
                                       fontSize = 5.sp,
                                       color = Color.White,

                                       )

                               }

                           }
                           Column(
                               modifier = Modifier
                                   .background(colorResource(R.color.grayLight))
                                   .weight(1f),
                               horizontalAlignment = Alignment.CenterHorizontally,

                           )
                           {
                               Text(
                                   text = "+1",
                                   fontSize = 5.sp,
                                   color = Color.Gray,
                               )
                           }



                       }
                   }


                }
                else {
                    break
                }
            }
        }





        // Cell Date Text (higher vertically – like bias 0.25)

        Box(
            modifier = Modifier
                .padding()
                .clip(RoundedCornerShape(10.dp))
                .background(if (dayNow) Color.Blue else Color.Transparent) // màu nền theo điều kiện
                .padding(6.dp) // thêm padding bên trong box
        ) {
            Text(
                text = dayText.toString(),
                color = if (dayNow) Color.White else Color.Black, // màu chữ theo điều kiện
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }




    }

}
