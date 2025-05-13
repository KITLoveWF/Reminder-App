package com.example.remiderapp
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ViewModel.TaskViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WeekCalendarScreen(navController: NavController) {
    MaterialTheme {
        Surface {
            val taskViewModel: TaskViewModel = viewModel()
            val _tasks = taskViewModel.getTask().observeAsState(emptyList())
            val tasks = _tasks.value
            var pendingTasks = tasks.filter { !it.complete }
            //val tasks by viewModel.incompleteTasks.observeAsState(emptyList())
            WeekCalendarScreen(tasks = pendingTasks,navController=navController)

        }
    }
}



@Composable
fun ArrowLeftButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = R.drawable.arrowleft),
            contentDescription = "Arrow Left",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ArrowRightButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = R.drawable.arrowright),
            contentDescription = "Arrow Right",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CalendarHeader(
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onSwitchToMorning: () -> Unit,
    onSwitchToPage2: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nút mũi tên trái
        ArrowLeftButton(onClick = onPreviousWeek)

        // Nút mũi tên phải
        ArrowRightButton(onClick = onNextWeek)
    }
}

@Composable
fun WeekCalendarScreen(tasks: List<Task>,
                       navController: NavController,
                       onPreviousWeek: () -> Unit = {},
                       onNextWeek: () -> Unit = {},
                       onSwitchToMorning: () -> Unit = {},
                       onSwitchToPage2: () -> Unit = {},

) {
    val today = LocalDate.now()
    val weekDates = remember { getWeekDates(today) }
    val scrollState = rememberScrollState()
    var selectedDate by remember { mutableStateOf(today) }
    Log.d("task","${tasks}")
    Scaffold { paddingValues ->
        Column(Modifier.fillMaxSize().padding(paddingValues)) {
            Column(modifier = Modifier.fillMaxWidth().weight(13f)){
                CalendarHeader(
                    onPreviousWeek = onPreviousWeek,
                    onNextWeek = onNextWeek,
                    onSwitchToMorning = onSwitchToMorning,
                    onSwitchToPage2 = onSwitchToPage2
                )
                // Header: ngày trong tuần
                Row(
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                        .padding(top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(48.dp))
                    weekDates.forEach { date ->
                        val isToday = date == today
                        val isSelected = date == selectedDate
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isSelected -> MaterialTheme.colorScheme.primary
                                        else -> Color.Transparent
                                    }
                                )
                                .clickable { selectedDate = date },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = getShortDayOfWeekVi(date.dayOfWeek),
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                text = date.dayOfMonth.toString(),
                                color = when {
                                    isSelected -> Color.White
                                    isToday -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.onSurface
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
                Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)

                // Lưới thời gian và công việc
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                ) {
                    for (hour in 0..23) {
                        TimeSlotRow(
                            hour = hour,
                            weekDates = weekDates,
                            tasks = tasks,
                            selectedDate = selectedDate
                        )
                    }
                }

            }

            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Footer(navController)
            }
        }

    }
}

@Composable
private fun TimeSlotRow(
    hour: Int,
    weekDates: List<LocalDate>,
    tasks: List<Task>,
    selectedDate: LocalDate
) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Cột hiển thị giờ
        Text(
            text = formatHour(hour),
            modifier = Modifier
                .width(48.dp)
                .padding(top = 8.dp),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End
        )

        // Các ô thời gian trong tuần
        weekDates.forEach { date ->
            val dailyTasks = tasks.filter {
                it.day == date.format(formatter) &&
                        it.time.startsWith(String.format("%02d", hour))
            }

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(2.dp)
            ) {
                dailyTasks.forEach { task ->
                    TaskEventItem(task = task)
                }
                Divider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 0.8.dp,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
fun TaskEventItem(task: Task) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
            if (task.content.isNotEmpty()) {
                Text(
                    text = task.content,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
            Text(
                text = task.time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Hàm hỗ trợ
private fun formatHour(hour: Int): String {
    return when {
        hour == 0 -> "12 AM"
        hour < 12 -> "$hour AM"
        hour == 12 -> "12 PM"
        else -> "${hour - 12} PM"
    }
}

private fun getWeekDates(today: LocalDate): List<LocalDate> {
    val firstDayOfWeek = DayOfWeek.MONDAY // Thay đổi thành MONDAY để tuần bắt đầu từ Thứ 2
    val startOfWeek = today.with(DayOfWeek.MONDAY)
    return (0..6).map { startOfWeek.plusDays(it.toLong()) }
}

private fun getShortDayOfWeekVi(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "T2"
        DayOfWeek.TUESDAY -> "T3"
        DayOfWeek.WEDNESDAY -> "T4"
        DayOfWeek.THURSDAY -> "T5"
        DayOfWeek.FRIDAY -> "T6"
        DayOfWeek.SATURDAY -> "T7"
        DayOfWeek.SUNDAY -> "CN"
    }
}
