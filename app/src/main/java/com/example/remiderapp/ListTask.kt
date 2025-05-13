package com.example.remiderapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
            // Modern AppBar with gradient and rounded bottom corners
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                    )
                    .shadow(4.dp, RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Nhắc nhở",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    navigationIcon = {
                        if (isOpenFooter) {
                            IconButton(onClick = {
                                isOpenFooter = !isOpenFooter
                                completedTasks.forEach { task -> onUpdateTask(task) }
                            }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            isOpenFooter = !isOpenFooter
                            if (isOpenFooter) {
                                pendingTasks.forEach { task -> onUpdateTask(task) }
                            } else {
                                completedTasks.forEach { task -> onUpdateTask(task) }
                            }
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                )
            }
        },
        floatingActionButton = {
            // Modern FAB: rounded rectangle, vibrant color, shadow
            FloatingActionButton(
                onClick = {
                    isChecked = !isChecked
                    navController.navigate("page4")
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(10.dp),
                modifier = Modifier
                    .navigationBarsPadding()
                    .size(width = 64.dp, height = 56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm Task")
            }
        },
        bottomBar = {
            if (isOpenFooter) {
                FooterTask(
                    completedTasks,
                    onDimiss = {
                        isOpenFooter = !isOpenFooter
                    }
                )
            } else {
                Footer(navController)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            // Search bar
            Column(modifier = Modifier.weight(2f)) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Tìm kiếm",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    shape = RoundedCornerShape(24.dp)
                )
            }

            // Folder filter row
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Button(
                        onClick = { isSelectFolder = -1 },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelectFolder == -1)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("All")
                    }

                    folders.forEach { category ->
                        Button(
                            onClick = { isSelectFolder = category.id },
                            modifier = Modifier.padding(end = 8.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelectFolder == category.id)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(category.name)
                        }
                    }
                    Button(
                        onClick = { navController.navigate("page6") },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.folder),
                            contentDescription = "More",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            }

            // Task lists
            Column(modifier = Modifier.weight(10f)) {
                Text(
                    "Nhiệm vụ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                    Text(
                        "Đã hoàn thành",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
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
