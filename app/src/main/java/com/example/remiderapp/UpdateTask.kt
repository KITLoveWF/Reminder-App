package com.example.remiderapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ViewModel.CategoryViewModel
import com.example.remiderapp.ViewModel.TaskViewModel
import kotlinx.coroutines.delay
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UpdateTask(navController: NavController,taskId: Int?)
{

    Log.d("Ziiiii","${taskId}")


    val taskViewModel: TaskViewModel = viewModel()


    var task by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId) {
        try {
            if (taskId != null) {
                Log.d("UpdateTask", "Đang gọi findTaskID với ID: $taskId")
                val fetchedTask = taskViewModel.findTaskID(taskId)
                Log.d("UpdateTask", "Kết quả findTaskID: $fetchedTask")
                task = fetchedTask
            } else {
                Log.e("UpdateTask", "taskId là null")
            }
        } catch (e: Exception) {
            Log.e("UpdateTask", "Lỗi trong LaunchedEffect: ${e.message}", e)
        }
    }

    val TASK =task

    Log.d("task","${TASK}")

    val context = LocalContext.current






    var expandedTime by remember { mutableStateOf(false) }
    var expnadedPriority by remember { mutableStateOf(false) }

    var isOpenSetTime by remember { mutableStateOf(false) }
    var cancelTime by remember { mutableStateOf(false) }


    val scrollState = rememberScrollState()

    val categoryViewModel: CategoryViewModel = viewModel()
    val _folders = categoryViewModel.getCategory().observeAsState(emptyList())
    val folders = _folders.value


    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(-1) }
    var dateTask by remember { mutableStateOf("") }
    var timeTask by remember { mutableStateOf("") }
    var reminder by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(TASK) {
        Log.d("UpdateTask", "LaunchedEffect(TASK) chạy với TASK: $TASK")
        if (TASK != null) {
            title = TASK.title
            content = TASK.content
            TASK.priority?.let { it->
                priority = it
            }
            dateTask = TASK.day
            timeTask = TASK.time
            selectedCategory = TASK.categoryId
            reminder = TASK.reminder
            Log.d("UpdateTask", "Đã cập nhật các biến từ TASK")
        } else {
            Log.e("UpdateTask", "TASK vẫn là null trong LaunchedEffect(TASK)")
//            Toast.makeText(context, "Không tìm thấy task", Toast.LENGTH_SHORT).show()
            // Thử delay trước khi quay lại
            delay(500)
            navController.popBackStack()
        }
    }









     //setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Update Task")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black, // Màu nền
                titleContentColor = Color.White // Màu chữ
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("page1")
                }) {
                    //Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    Icon(
                        painter = painterResource(id = R.drawable.arrow), // replace with your icon
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    expnadedPriority =! expnadedPriority


                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Độ ưu tin", tint = Color.White)
                }

                DropdownMenu(
                    expanded = expnadedPriority,
                    onDismissRequest = { expnadedPriority = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Priority 1") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.priority_1), // replace with your icon
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Red
                            )
                        },
                        onClick = {
                            /* Do something... */
                            priority = 1
                            expnadedPriority =! expnadedPriority
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Priority 2") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.priority_2), // replace with your icon
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Yellow

                            )
                        },
                        onClick = { /* Do something... */
                            priority = 2
                            expnadedPriority =! expnadedPriority
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Priority 3") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.priority_3), // replace with your icon
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Blue

                            )
                        },
                        onClick = { /* Do something... */
                            priority = 3
                            expnadedPriority =! expnadedPriority
                        }
                    )
                }


                IconButton(onClick = {
                    isOpenSetTime =!isOpenSetTime
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Lăp hàng tuần", tint = Color.White)
                }
                IconButton(onClick = {
                    //val task = Task(0,title,content,timeTask,dateTask,cancelTime,priority,false)
                    TASK?.let{
                        val updated = it.copy(
                            title = title,
                            content = content,
                            time = timeTask,
                            day = dateTask,
                            reminder = cancelTime,
                            priority = priority,
                            complete = TASK.complete,
                            categoryId = selectedCategory
                        )
                        //Log.d("xin chào", "id=${it.id}, title=${title}, content=${it.content}, time=${it.time}, day=${it.day}, reminder=${it.reminder}, priority=${it.priority}, complete=${it.complete}, categoryId=${it.categoryId}")

                        taskViewModel.updateTask(updated)
                    }

                    navController.navigate("page1")
                }) {
                    Icon(Icons.Default.Check, contentDescription = "Tạo nhiệm vụ", tint = Color.White)
                }
            }


        )

    }) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .background(Color.Black)
            .fillMaxSize()){
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(8.dp)

                )
                {

                    Button(
                        onClick = { /* TODO: xử lý từng loại */ },
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors =  ButtonDefaults.buttonColors(Color.Gray)

                    ) {
                        Text("All")
                    }

                    folders.forEach { category ->
                        Button(
                            onClick = { selectedCategory = category.id },
                            modifier = Modifier
                                .padding(end = 8.dp),
                            colors =  ButtonDefaults.buttonColors(Color.Gray)

                        ) {
                            Text(category.name)
                        }
                    }
                }


            }
            Column(modifier = Modifier.weight(1f)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Black,
                        focusedTextColor = Color.White,
                        cursorColor = Color.Yellow,
                        unfocusedTextColor = Color.Gray,
                        // màu placeholder
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold             // độ đậm
                    ),
                    placeholder = {
                        Text(
                            text = "Tiêu đề",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,       // placeholder thường mỏng hơn
                            color = Color.Gray
                        )
                    },
                    shape = RectangleShape,
                    singleLine = false,
                    maxLines = 5
                )

            }
            Column(modifier = Modifier.weight(11f)) {
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Black,
                        focusedTextColor = Color.White,
                        cursorColor = Color.Yellow,
                        unfocusedTextColor = Color.Gray,

                        // màu placeholder
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold             // độ đậm
                    ),
                    placeholder = {
                        Text(
                            text = "Bắt đầu soạn",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,       // placeholder thường mỏng hơn
                            color = Color.Gray
                        )
                    },
                    shape = RectangleShape,
                    singleLine = false,
                    maxLines = Int.MAX_VALUE
                )

            }
            InputValue(
                showDialog = isOpenSetTime,
                onConfirm = {
                        date,time ->
                    dateTask = date
                    timeTask = time
                },
                onDismiss = {
                    isOpenSetTime = false

                },
                title = title,
                content = content,
                cancelTime = reminder,
                onCancelTimeChange = { reminder = it }
            )

        }
    }

}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InputValue(
//    showDialog: Boolean,
//    onDismiss: () -> Unit,
//    onConfirm: (String,String) -> Unit,
//    title: String,
//    content:String
//)
//{
//    val context = LocalContext.current
//    LaunchedEffect(Unit) {
//        createNotificationChannel(context)
//    }
//    // setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")
//    // Chọn ngày
//    var dateTask by remember { mutableStateOf("") }
//    var day by remember { mutableStateOf(0) }
//    var month by remember { mutableStateOf(0) }
//    var year by remember { mutableStateOf(0) }
////    val datePickerState = rememberDatePickerState(
////        day,month, yearRange = IntRange(1900,2300),
////        initialDisplayMode = DisplayMode.Picker,
////    )
//
//    val datePickerState = rememberDatePickerState(
//        //day,month, yearRange = IntRange(1900,2300),
//        initialDisplayMode = DisplayMode.Picker,
//    )
//
//
//
//    // chọn giờ
//    var timeTask by remember { mutableStateOf("") }
//    var hour by remember { mutableStateOf(0) }
//    var minute by remember { mutableStateOf(0) }
//    val timePickerState = rememberTimePickerState(
//        hour, minute, is24Hour = true
//    )
//    var isOpenDate by remember { mutableStateOf(false) }
//    var isOpenTime by remember { mutableStateOf(false) }
//
//
//    if(showDialog)
//    {
//        AlertDialog(
//            onDismissRequest = onDismiss,
//            title = { Text("Set schedule time") },
//            text = {
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    //DatePicker(state = dtp)
//                    Button(
//                        onClick = {
//                            isOpenDate=!isOpenDate
//                        },
//                        modifier = Modifier.width(150.dp).height(80.dp),
//
//                        ) {
//                        Text("Set date", fontSize = 20.sp )
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Button(
//                        onClick = {
//                            isOpenTime =!isOpenTime
//
//                        },
//                        modifier = Modifier.width(150.dp).height(80.dp)
//                    ) {
//                        Text("Set time",fontSize = 20.sp )
//                    }
//                    //TimePicker(state = timePickerState)
//
//
//                }
//
//            },
//            confirmButton = {
//                TextButton(
//                    onClick = {
////                        val select = datePickerState.selectedDateMillis
////                        date = select?.let{
////                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
////                            sdf.format(Date(it))
////                        }.toString()
////                        time = String.format("%02d:%02d",timePickerState.hour,timePickerState.minute)
//
//                        Log.d("Time", "year=$year, month=$month, day=$day, hour=$hour, minute=$minute, title=$title, content=$content")
//                        setTime(context = context,year,month,day,hour,minute,title,content)
//
//                        // setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")
//                        onConfirm(dateTask,timeTask)
//                        onDismiss()
//                    }
//                ) {
//                    Text("Ok")
//                }
//
//            },
//            dismissButton = {
//                TextButton(onClick = onDismiss) {
//                    Text("Cancel")
//                }
//
//            },
//        )
//        showDate(datePickerState= datePickerState,
//            showDialog = isOpenDate,
//            onConfirm ={
//                    Date,Day,Month,Year ->
//                dateTask = Date
//                day = Day
//                month = Month
//                year = Year
//
//
//            } ,
//            onDismiss = {
//                isOpenDate = false
//            }
//        )
//        showTime(timePickerState,
//            showDialog = isOpenTime,
//            onConfirm ={
//                    Time,Hour,Minute->
//                timeTask = Time
//                hour = Hour
//                minute = Minute
//            } ,
//            onDismiss = {
//                isOpenTime = false
//            })
//
//    }
//
//
//}
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun showDate(datePickerState: DatePickerState,
//             showDialog: Boolean,
//             onDismiss: () -> Unit,
//             onConfirm: (String,Int,Int,Int) -> Unit)
//{
//    var date by remember { mutableStateOf("") }
//    if(showDialog)
//    {
//
//        AlertDialog(
//            onDismissRequest = onDismiss,
//            title = { Text("Set Date") },
//            text ={
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    DatePicker(state = datePickerState)
//
//                }
//
//            },
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        val select = datePickerState.selectedDateMillis
//                        var month = -1
//                        var year = -1
//                        var day = -1
//                        date = select?.let{
//                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//                            sdf.format(Date(it))
//                        }.toString()
//
//
//                        val selectedDateMillis = datePickerState.selectedDateMillis
//                        selectedDateMillis?.let { millis ->
//                            val calendar = Calendar.getInstance().apply {
//                                timeInMillis = millis
//                            }
//                            day = calendar.get(Calendar.DAY_OF_MONTH)
//                            month = calendar.get(Calendar.MONTH) + 1
//                            year = calendar.get(Calendar.YEAR)
//
//                            onConfirm(date,day,month,year)
//                        }
//
//                        onDismiss()
//                    }
//                ) {
//                    Text("Ok")
//                }
//
//            },
//            dismissButton = {
//                TextButton(onClick = onDismiss) {
//                    Text("Cancel")
//                }
//
//            },
//
//            )
//    }
//
//
//}
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun showTime(timePickerState: TimePickerState,
//             showDialog: Boolean,
//             onDismiss: () -> Unit,
//             onConfirm: (String,Int,Int) -> Unit
//)
//{
//    var time by remember { mutableStateOf("") }
//    if(showDialog) {
//
//        AlertDialog(
//            onDismissRequest = onDismiss,
//            title = { Text("Set time") },
//            text = {
//                Column {
//                    TimePicker(state = timePickerState)
//                }
//
//            },
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        time = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
//                        var hour = timePickerState.hour
//                        var minute = timePickerState.minute
//                        Log.d("dmmmmmm","hour=$hour,minute=$minute")
//
//                        //setTime(context = context,year,month,day,hour,minute,title,content)
//
//                        // setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")
//                        onConfirm(time,hour,minute)
//                        onDismiss()
//                    }
//                ) {
//                    Text("Ok")
//                }
//
//            },
//            dismissButton = {
//                TextButton(onClick = onDismiss) {
//                    Text("Cancel")
//                }
//
//            },
//
//            )
//    }
//
//}
//
//
//fun setTime(context: Context, year: Int, month: Int, day: Int, hour: Int, minute: Int, title:String, content: String)
//{
//    //Log.d("Timekkk", "year=$year, month=$month, day=$day, hour=$hour, minute=$minute, title=$title, content=$content")
//
//    val calendar = Calendar.getInstance().apply {
//        set(Calendar.YEAR, year)
//        set(Calendar.MONTH, month-1) // 0-11
//        set(Calendar.DAY_OF_MONTH, day)
//        set(Calendar.HOUR_OF_DAY, hour)
//        set(Calendar.MINUTE, minute)
//        set(Calendar.SECOND, 0)
//        set(Calendar.MILLISECOND, 0)
//    }
//
//    scheduleNotification(context,calendar,title,content)
//
//}
//fun scheduleNotification(context: Context?, calendar: Calendar, title:String, content:String)
//{
//    //Log.d("Timekkk", "year=$, month=$month, day=$day, hour=$hour, minute=$minute, title=$title, content=$content")
////    val logText = "Year=${calendar.get(Calendar.YEAR)}, " +
////            "Month=${calendar.get(Calendar.MONTH)}, " + // +1 vì bắt đầu từ 0
////            "Day=${calendar.get(Calendar.DAY_OF_MONTH)}, " +
////            "Hour=${calendar.get(Calendar.HOUR_OF_DAY)}, " +
////            "Minute=${calendar.get(Calendar.MINUTE)}, " +
////            "Second=${calendar.get(Calendar.SECOND)}"
////
////    Log.d("CalendarLog", logText)
////    Log.d("Timehhhh","${calendar.time}")
//    val intent = Intent(context, Notification::class.java)
//    intent.putExtra(titleExtra,title)
//    intent.putExtra(messageExtra,content)
//
//    val pendingIntent = PendingIntent.getBroadcast(
//        context,
//        1,
//        intent,
//        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//    )
//    val alarmManager = context?.let{
//        it.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    }
//    alarmManager?.let {
//        it.setAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            pendingIntent
//        )
//    }
//    Toast.makeText(context, "Alarm set for ${calendar.time}", Toast.LENGTH_SHORT).show()
//}
//
//fun createNotificationChannel(context: Context?)
//{
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "Alarm Channel"
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val desc = "Description of channel"
//        val channel = NotificationChannel("channel1", name, importance)
//        channel.description = desc
//
//        val notificationManager = context?.let{
//            it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        }
//        notificationManager?.let{
//            it.createNotificationChannel(channel)
//        }
//    }
//}
