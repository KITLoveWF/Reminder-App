package com.example.remiderapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remiderapp.Model.Task
import java.time.LocalDate

@Composable
@Preview(showBackground = true)
fun CalendarScreen(
    monthText: String = "Apr 2025",
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {

        // Month navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onPreviousClick,
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
                onClick = onNextClick,
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
        CalendarGrid(month = 4, year = 2025)
    }
}

@Composable
fun CalendarGrid(month: Int, year: Int) {
    // Placeholder example with LazyVerticalGrid (requires Accompanist/Material3)
    val daysInMonth = remember { getDaysInMonth(year, month) }
    val taskList = mutableListOf(
//        // Ngày 1: 2025-04-23
//        Task(name = "Làm bài tập Toán", content = "Giải 5 bài hình học", time = "08:00", day = "2025-04-23"),
//        Task(name = "Học Tiếng Anh", content = "Ôn từ vựng Unit 5", time = "09:30", day = "2025-04-23"),
//        Task(name = "Họp nhóm", content = "Chuẩn bị bài thuyết trình", time = "14:00", day = "2025-04-23"),
//        Task(name = "Đi siêu thị", content = "Mua đồ ăn cho tuần", time = "17:00", day = "2025-04-23"),
//        Task(name = "Lập kế hoạch tuần", content = "Ghi chú lịch trình công việc", time = "20:00", day = "2025-04-23"),
//
//        // Ngày 2: 2025-04-24
//        Task(name = "Đọc sách", content = "Chương 3 - Tư duy phản biện", time = "07:00", day = "2025-04-24"),
//        Task(name = "Viết blog", content = "Chia sẻ kinh nghiệm học lập trình", time = "10:00", day = "2025-04-24"),
//        Task(name = "Tập thể dục", content = "Chạy bộ 3km", time = "12:00", day = "2025-04-24"),
//        Task(name = "Học lập trình", content = "Compose Basics", time = "16:00", day = "2025-04-24"),
//        Task(name = "Xem phim", content = "Xem phim tài liệu lịch sử", time = "21:00", day = "2025-04-24"),

        // Ngày 3: 2025-04-25
        Task(name = "Dọn dẹp phòng", content = "Sắp xếp sách vở", time = "08:00", day = "2025-04-25"),
        Task(name = "Làm đồ án", content = "Phân tích yêu cầu đề tài", time = "11:00", day = "2025-04-25"),
        Task(name = "Đi cà phê", content = "Gặp bạn bè thư giãn", time = "13:00", day = "2025-04-25"),
        Task(name = "Tham gia workshop", content = "Chủ đề UI/UX", time = "15:00", day = "2025-04-25"),
        Task(name = "Tự học", content = "Xem tutorial về Kotlin", time = "19:00", day = "2025-04-25")
    )


    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxSize()
    ) {
        items(daysInMonth.size) { index ->

            if(daysInMonth[index] != LocalDate.MIN) {


                if (daysInMonth[index].toString() == taskList[0].day) {
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


//            Box(
//                modifier = Modifier
//                    .aspectRatio(1f)
//                    .padding(2.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                if(daysInMonth[index] != LocalDate.MIN)
//                {
//                    Text(text = daysInMonth[index].toString())
//                }
//
//            }
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
