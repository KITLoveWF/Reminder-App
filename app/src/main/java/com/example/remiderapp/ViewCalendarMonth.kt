package com.example.remiderapp

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ViewModel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


enum class Month {
   Jan,Fer,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
}
@Composable
//@Preview(showBackground = true)
fun CalendarScreen(
    navController: NavController
) {
    val calendar = Calendar.getInstance()
    var month by  remember { mutableStateOf(calendar.get(Calendar.MONTH) + 1)}
    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR))}
    var monthText by remember { mutableStateOf("") }
    when(month)
    {
        1->{
            monthText = Month.Jan.name + " ${year}"
        }
        2->{
            monthText = Month.Fer.name + " ${year}"

        }
        3->{
            monthText = Month.Mar.name + " ${year}"
        }
        4->{
            monthText = Month.Apr.name + " ${year}"
        }
        5->{
            monthText = Month.May.name + " ${year}"
        }
        6->{
            monthText = Month.Jun.name + " ${year}"
        }
        7->{
            monthText = Month.Jul.name + " ${year}"

        }
        8->{
            monthText = Month.Aug.name + " ${year}"

        }
        9->{
            monthText = Month.Sep.name + " ${year}"
        }
        10->{
            monthText = Month.Oct.name + " ${year}"
        }
        11->{
            monthText = Month.Nov.name + " ${year}"
        }
        12->{
            monthText = Month.Dec.name + " ${year}"
        }

    }
//    onPreviousClick: () -> Unit = {},
//    onNextClick: () -> Unit = {},
    Scaffold {
        paddingValues->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(modifier = Modifier.fillMaxWidth().weight(13f)) {
                // Month navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if(month>1)
                            {
                                month = month - 1
                            }
                            else if(month == 1)
                            {
                                month = 12
                                year = year - 1
                            }
                            Log.d("time","month:${month},year:${year}")

                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Text("<-", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        text = monthText,
                        modifier = Modifier.weight(2f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            if(month<12)
                            {
                                month = month + 1
                            }
                            else if(month == 12)
                            {
                                month = 1
                                year = year + 1
                            }

                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Text("->", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Day headers
                val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")
                Row(modifier = Modifier.fillMaxWidth()) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // RecyclerView replacement (LazyVerticalGrid, LazyColumn, etc.)
                Log.d("timekkk","month:${month},year:${year}")
                CalendarGrid(month = month, year = year)

            }
            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Footer(navController)
            }

        }

    }

}

@Composable
fun CalendarGrid(month: Int, year: Int) {
    // Placeholder example with LazyVerticalGrid (requires Accompanist/Material3)
    val daysInMonth = remember(month, year) { getDaysInMonth(year, month) }
//    val taskList = mutableListOf(
//        // Ngày 3: 2025-04-25
//        Task(
//            id = 1,
//            title = "Dọn dẹp phòng",
//            content = "Sắp xếp sách vở",
//            time = "08:00",
//            day = "2025-04-25",
//            reminder = true,
//            priority = 1,
//            complete = false
//        ),
//        Task(
//            id = 2,
//            title = "Làm đồ án",
//            content = "Phân tích yêu cầu đề tài",
//            time = "11:00",
//            day = "2025-04-25",
//            reminder = true,
//            priority = 2,
//            complete = false
//        ),
//        Task(
//            id = 3,
//            title = "Đi cà phê",
//            content = "Gặp bạn bè thư giãn",
//            time = "13:00",
//            day = "2025-04-25",
//            reminder = false,
//            priority = 3,
//            complete = false
//        ),
//        Task(
//            id = 4,
//            title = "Tham gia workshop",
//            content = "Chủ đề UI/UX",
//            time = "15:00",
//            day = "2025-04-25",
//            reminder = true,
//            priority = 1,
//            complete = false
//        ),
//        Task(
//            id = 5,
//            title = "Tự học",
//            content = "Xem tutorial về Kotlin",
//            time = "19:00",
//            day = "2025-04-25",
//            reminder = false,
//            priority = 2,
//            complete = false
//        )
//    )
    val taskViewModel: TaskViewModel = viewModel()
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxSize()
    ) {
        items(daysInMonth.size) { index ->



            // Sử dụng remember để giữ trạng thái taskList theo từng ngày
            var taskList by remember { mutableStateOf<List<Task>>(emptyList()) }


            if (daysInMonth[index] != LocalDate.MIN) {
                val formattedDate = convertDateFormat(daysInMonth[index].toString())
                LaunchedEffect(daysInMonth[index]) {

                    taskList = taskViewModel.findTaskDay(formattedDate)
                    Log.d("TASK", "$taskList")
                    Log.d("TASKhhh", "${formattedDate}")
                    if(taskList.isNotEmpty())
                    {
                        Log.d("xin chào",taskList[0].day)
                    }


                }
                //Log.d("TASKhhh", "${daysInMonth[index]}")

                if (taskList.isNotEmpty() && formattedDate == taskList[0].day) {

                    if (daysInMonth[index] == LocalDate.now()) {
                        TaskItem(daysInMonth[index].dayOfMonth, taskList, true);
                    } else {
                        TaskItem(daysInMonth[index].dayOfMonth, taskList, false);
                    }

                } else {
                    if (daysInMonth[index] == LocalDate.now()) {
                        TaskItem(daysInMonth[index].dayOfMonth, null, true);

                    } else {
                        TaskItem(daysInMonth[index].dayOfMonth, null, false);
                    }
                }
            }
        }
    }
}
fun getDaysInMonth(year: Int, month: Int): List<LocalDate>
{
    val start = LocalDate.of(year, month, 1)
    val end = start.withDayOfMonth(start.lengthOfMonth())

    val days = mutableListOf<LocalDate>()

    // Bổ sung ngày trắng đầu tuần (nếu có) để khớp cột
    val startDayOfWeek = start.dayOfWeek.value % 7 // Chủ nhật là 0
    for (i in 0 until startDayOfWeek) {
        days.add(LocalDate.MIN) // hoặc null tuỳ cách xử lý
    }

    var current = start
    while (!current.isAfter(end)) {
        days.add(current)
        current = current.plusDays(1)
    }

    return days

}

fun convertDateFormat(input: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val date = LocalDate.parse(input, inputFormatter)
    return date.format(outputFormatter)
}
