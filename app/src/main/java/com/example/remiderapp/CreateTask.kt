package com.example.remiderapp

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.provider.AlarmClock
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

//import androidx.compose.icons.FeatherIcons
//import androidx.compose.icons.feathericons.Clock


@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CreateTask()
{
    var title by remember { mutableStateOf("") }
    var content by remember{mutableStateOf("")}
    var isDateRecurrence by remember { mutableStateOf(false) }
    var isWeekRecurrence by remember { mutableStateOf(false) }
    var expandedTime by remember { mutableStateOf(false) }
    var expnadedPriority by remember { mutableStateOf(false) }
    var priority by remember { mutableStateOf(0) }
    var dateTask by remember { mutableStateOf("") }
    var timeTask by remember { mutableStateOf("") }
    var isOpenSetTime by remember { mutableStateOf(false) }


    val context = LocalContext.current
    // setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Set Task")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black, // Màu nền
                titleContentColor = Color.White // Màu chữ
            ),
            navigationIcon = {
                IconButton(onClick = {
                    //navController.navigate("page1")
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
//                IconButton(onClick = {
//
//                }) {
//                    Icon(Icons.Default.Menu, contentDescription = "Độ ưu tiên", tint = Color.White)
//                }
                IconButton(onClick = {

                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Lặp hàng ngày", tint = Color.White)
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
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = {
                        /* Do something... */
                            priority = 1
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Priority 2") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.priority_2), // replace with your icon
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = { /* Do something... */
                            priority = 2
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Priority 3") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.priority_3), // replace with your icon
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = { /* Do something... */
                            priority = 3
                        }
                    )
                }


                IconButton(onClick = {
                    expandedTime = !expandedTime
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Lăp hàng tuần", tint = Color.White)
                }
                DropdownMenu(
                    expanded = expandedTime,
                    onDismissRequest = { expandedTime = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Set Time") },
                        onClick = {
                        /* Do something... */
                            InputValue(
                                showDialog = isOpenSetTime,
                                onConfirm = {
                                        date,time ->
                                    dateTask = date
                                    timeTask = time
                                },
                                onDismiss = {
                                    isOpenSetTime = false

                                }
                            )

                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Repeat date") },
                        onClick = {
                        /* Do something... */
                            isDateRecurrence = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Repeat week") },
                        onClick = {
                        /* Do something... */
                            isWeekRecurrence = true
                        }
                    )
                }
                IconButton(onClick = {
                    // lưu
                }) {
                    Icon(Icons.Default.Check, contentDescription = "Tạo nhiệm vụ", tint = Color.White)
                }
            }


        )

    }) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()){
            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
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
            Column(modifier = Modifier.fillMaxWidth().weight(11f)) {
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

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputValue(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String,String) -> Unit
)
{
    //val context = LocalContext.current
    // setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")
    // Chọn ngày
    var date by remember { mutableStateOf("") }
    val day by remember { mutableStateOf(null) }
    val month by remember { mutableStateOf(null) }
    val year by remember { mutableStateOf(null) }
    val dtp = rememberDatePickerState(
        day,month, yearRange = IntRange(1900,2300),
        initialDisplayMode = DisplayMode.Picker,
    )
    val select = dtp.selectedDateMillis
    date = select?.let{
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(Date(it))
    }.toString()


    // chọn giờ
    var time by remember { mutableStateOf("No time") }
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val minute = Calendar.getInstance().get(Calendar.MINUTE)
    val timePickerState = rememberTimePickerState(
        hour, minute, is24Hour = true
    )


    if(showDialog)
    {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Set schedule time") },
            text ={
                Column {
                    Text("Set Date")
                    DatePicker(state = dtp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Set Time")
                    TimePicker(state = timePickerState)


                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        time = String.format("%02d:%02d",timePickerState.hour,timePickerState.minute)
                        // setTime(context,timePickerState.hour,timePickerState.minute,"Wake up for class!")
                        onConfirm(date,time)
                        onDismiss()
                    }
                ) {
                    Text("Ok")
                }

            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }

            },

            )
    }


}



fun setTime(context: Context, year: Int, month: Int, day: Int, hour: Int, minute: Int, message: String)
{
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month) // 0-11
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val intent = Intent()
//    val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
//        putExtra(AlarmClock.EXTRA_HOUR,hour)
//        putExtra(AlarmClock.EXTRA_MINUTES,minute)
//        putExtra(AlarmClock.EXTRA_MESSAGE,message)
//        //putExtra(AlarmClock.EXTRA_SKIP_UI, true)
//    }
//    if (intent.resolveActivity(context.packageManager) == null) {
//        context.startActivity(intent)
//        Toast.makeText(context, "Alarm set for $hour:$minute", Toast.LENGTH_SHORT).show()
//    } else {
//        Toast.makeText(context, "No alarm app available", Toast.LENGTH_SHORT).show()
//    }
}
fun setDateRecurrence(context: Context, hour: Int, minute:Int, message: String)
{

}
fun setWeekRecurrence(context: Context, hour: Int, minute:Int, message: String)
{

}