package com.example.remiderapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ViewModel.CategoryViewModel
import com.example.remiderapp.ViewModel.TaskViewModel

@Composable
//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)

fun ListTask(navController: NavController)
{
    //navController: NavController

    var isChecked by remember { mutableStateOf(false) }
    var isSelectFolder by remember { mutableStateOf(-1) }
    var isOpenFooter by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val taskViewModel: TaskViewModel = viewModel()
    val _tasks = taskViewModel.getTask().observeAsState(emptyList())
    val tasks = _tasks.value

    val categoryViewModel: CategoryViewModel = viewModel()
    val _folders = categoryViewModel.getCategory().observeAsState(emptyList())
    val folders = _folders.value

    var completedTasks = tasks.filter { it.complete }
    var pendingTasks = tasks.filter { !it.complete }

    var compltetedTasksByCategory = tasks.filter { it.complete && it.categoryId == isSelectFolder }
    var pendingTasksByCategory = tasks.filter { !it.complete && it.categoryId == isSelectFolder }

    var searchQuery by remember { mutableStateOf("") }
    var listSearchQueryComplete  = tasks.filter {
        check(searchQuery,it.title) && it.complete
    }
    var listSearchQueryPending = tasks.filter {
        check(searchQuery,it.title) && !it.complete
    }
    var longPressedTask by remember { mutableStateOf<Task?>(null) }
    // ✅ Hàm xóa task
    fun onDeleteTask(task: Task) {
        taskViewModel.deleteTask(task)
        //pendingTasks = tasks.filter { it.id != task.id }
    }
    fun onUpdateTask(task:Task)
    {
        val updatedTask = task.copy(complete =!task.complete)
        tasks.map {
            if (it.id == task.id)
                taskViewModel.updateTask(updatedTask)
            else it
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Nhắc nhở")
                },
                navigationIcon = {
                    if(isOpenFooter)
                    {
                        IconButton(onClick = {
                            isOpenFooter = !isOpenFooter
                            completedTasks.forEach({
                                    task-> onUpdateTask(task)
                            })

                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Back", tint = colorResource(id = R.color.graymain) )
                        }

                    }

                },
                actions = {
                    IconButton(onClick = {
                        isOpenFooter =!isOpenFooter
                        if(isOpenFooter)
                        {
                            pendingTasks.forEach { task -> onUpdateTask(task)}
                        }
                        else{
                            completedTasks.forEach({
                                task-> onUpdateTask(task)
                            })
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black, // Màu nền
                    titleContentColor = Color.White // Màu chữ
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isChecked = !isChecked
                    navController.navigate("page4")

                },
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.navigationBarsPadding()


            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm Task")
//                if(!isChecked)
//                {
//
//
//                }
//                else{
//
//                    Icon(Icons.Default.Check, contentDescription = "Đánh dấu đã thêm")
//
//
//                }
            }

        },
        bottomBar = {
            if(isOpenFooter)
            {
                FooterTask(
//                    onDelete = {
//                        //onDeleteTask(task)
//                        longPressedTask = null
//                        isOpenFooter = false
//                    }
                    completedTasks,
//                    onMoveToCategory = {
//                        //onMoveToCategory(task)
//                        longPressedTask = null
//                        isOpenFooter = false
//                    },
                    onDimiss = {
                        isOpenFooter =!isOpenFooter
                    }
                )
            }
            else
            {
                Footer(navController)
            }

        }
    ) {
            paddingValues ->
        Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(paddingValues),

    ) {
            Column(modifier = Modifier.weight(2f)) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.DarkGray,
                        focusedTextColor = Color.White,
                        cursorColor = Color.Yellow,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Tìm kiếm",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,       // placeholder thường mỏng hơn
                            color = Color.Gray
                        )
                    },
                )

            }
            Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(8.dp))
            {

                Button(
                    onClick = {
                        isSelectFolder = -1
                    },
                    modifier = Modifier
                        .padding(end = 8.dp),
                    colors =  ButtonDefaults.buttonColors(Color.Gray)

                ) {
                    Text("All")
                }

                folders.forEach { category ->
                    Button(
                        onClick = { isSelectFolder = category.id },
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors =  ButtonDefaults.buttonColors(Color.Gray)

                    ) {
                        Text(category.name)
                    }
                }
                // Nút cuối cùng chuyển sang danh sách thư mục
                Button(
                    onClick = {
                        // Ví dụ nếu dùng NavController:
                        navController.navigate("page6")
                    },
                    modifier = Modifier
                        .padding(end = 8.dp),
                    colors =  ButtonDefaults.buttonColors(Color.Gray)


                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.folder), // replace with your icon
                        contentDescription = "More",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Yellow
                    )
                }

            } }
            Column (modifier = Modifier.weight(10f)){
                Text("Nhiệm vụ", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp), color = Color.White)
                LazyColumn {
                    if(searchQuery=="")
                    {
                        if (isSelectFolder == -1)
                        {
                            items(pendingTasks.size) { index ->
                                val task = pendingTasks[index]
                                TaskRow (
                                    task = task,
                                    onCheckedChange = {
                                        onUpdateTask(task)
                                        isOpenFooter = !isOpenFooter
                                        //longPressedTask = task
                                    },
                                    onClick = {
                                        //taskViewModel.selectedTask = task
                                        navController.navigate("page5/${task.id}")
                                    }
                                )
                            }

                        }
                        else
                        {
                            items(pendingTasksByCategory.size) { index ->
                                val task = pendingTasksByCategory[index]
                                TaskRow (
                                    task = task,
                                    onCheckedChange = {
                                        onUpdateTask(task)
                                        isOpenFooter = !isOpenFooter
                                        //longPressedTask = task
                                    },
                                    onClick = {
                                        //taskViewModel.selectedTask = task
                                        navController.navigate("page5/${task.id}")
                                    }
                                )
                            }

                        }
                    }
                    else
                    {
                        Log.d("search","${listSearchQueryPending}")
                            items(listSearchQueryPending.size) { index ->
                                val task = listSearchQueryPending[index]
                                TaskRow (
                                    task = task,
                                    onCheckedChange = {
                                        onUpdateTask(task)
                                        isOpenFooter = !isOpenFooter
                                        //longPressedTask = task
                                    },
                                    onClick = {
                                        //taskViewModel.selectedTask = task
                                        navController.navigate("page5/${task.id}")
                                    }
                                )
                            }
                    }


                }

                if (completedTasks.isNotEmpty()) {
                    Text("Đã hoàn thành", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp), color = Color.White)
                    LazyColumn {
                        if(searchQuery=="")
                        {
                            if (isSelectFolder == -1) {
                                items(completedTasks.size) { index ->
                                    val task = completedTasks[index]
                                    TaskRow(
                                        task = task,
                                        onCheckedChange = {
                                            onUpdateTask(task)
                                            isOpenFooter = !isOpenFooter
                                        },
//                                onLongPress = {
//                                    longPressedTask = task
//                                },
                                        onClick = {
                                            taskViewModel.selectedTask = task

                                        }
                                    )
                                }
                            }
                            else
                            {
                                items(compltetedTasksByCategory.size) { index ->
                                    val task = compltetedTasksByCategory[index]
                                    TaskRow(
                                        task = task,
                                        onCheckedChange = {
                                            onUpdateTask(task)
                                            isOpenFooter = !isOpenFooter
                                        },
//                                onLongPress = {
//                                    longPressedTask = task
//                                },
                                        onClick = {
                                            taskViewModel.selectedTask = task

                                        }
                                    )
                                }

                            }
                        }
                        else
                        {

                                items(listSearchQueryComplete.size) { index ->
                                    val task = listSearchQueryComplete[index]
                                    TaskRow(
                                        task = task,
                                        onCheckedChange = {
                                            onUpdateTask(task)
                                            isOpenFooter = !isOpenFooter
                                        },
//                                onLongPress = {
//                                    longPressedTask = task
//                                },
                                        onClick = {
                                            taskViewModel.selectedTask = task

                                        }
                                    )
                                }

                        }


                    }
                }


            }



    }


    }
}

fun check(sub: String, pa: String): Boolean {
    for (i in sub.indices) {
        for (j in i + 1..sub.length) {
            val result = sub.substring(i, j)
            Log.d("result",result)
            if (pa.contains(result, ignoreCase = true)) {
                return true
            }
        }
    }
    return false
}
