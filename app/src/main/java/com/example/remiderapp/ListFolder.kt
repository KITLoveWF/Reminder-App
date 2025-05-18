package com.example.remiderapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.remiderapp.Model.Category
import com.example.remiderapp.ViewModel.CategoryViewModel
import com.example.remiderapp.ViewModel.TaskViewModel
import androidx.compose.ui.text.font.FontWeight

@Composable
//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
fun ListFolder(navController:NavController)
{
    //navController:NavController
    var selectedIndex = remember { mutableStateListOf<Category>() }
//    val folders = remember {
//        mutableStateListOf(
//            "Tất cả" to 19,
//            "Thư mục Ghi chú" to 1,
//            "Chưa phân loại" to 18
//        )
//    }
    val categoryViewModel: CategoryViewModel = viewModel()
    val _folders = categoryViewModel.getCategory().observeAsState(emptyList())
    val folders = _folders.value

    val taskViewModel: TaskViewModel = viewModel()
//    LaunchedEffect(currentImageUri.value) {
//         = imageViewModel.findImage(currentImageUri.value) // Gọi suspend fun trong coroutine scope
//        Log.d("Test", "Image: ${image?.uri}")
//    }

    var content by remember{ mutableStateOf("") }
    var isOpenCreateFolder by remember { mutableStateOf(false) }
    var isOpenFooter by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
//    if(isOpenCreateFolder == false && content!="")
//    {
//        folders.add(content to 0)
//        content = ""
//    }
    Scaffold(
        topBar = {
            // Modern, colorful, rounded AppBar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .shadow(4.dp, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Thư mục",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate("page1")
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            isOpenFooter = !isOpenFooter
                            if (isOpenFooter) {
                                folders.forEach { category -> selectedIndex.add(category) }
                            } else {
                                selectedIndex.clear()
                            }
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                )
            }
        },

        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (isOpenFooter) {
                FooterFolder(selectedIndex)
            }
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
        ) {
            folders.forEach { category ->
                val isSelected = selectedIndex.contains(category)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    isOpenFooter = true
                                    if (!selectedIndex.contains(category)) selectedIndex.add(category)
                                },
                                onTap = {
                                    isOpenFooter = false
                                    selectedIndex.remove(category)
                                },
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                        Text(
                            text = category.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Thư mục mới (New Folder)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        isOpenCreateFolder = !isOpenCreateFolder
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.addfolder),
                        contentDescription = "Add Folder",
                        modifier = Modifier.size(24.dp).padding(end = 8.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Thư mục mới",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
    CreateFolder(
        onDismiss = {
            isOpenCreateFolder = false
        },
        Save = {
                it->content = it
        },
        isOpenCreateFolder
    )

}