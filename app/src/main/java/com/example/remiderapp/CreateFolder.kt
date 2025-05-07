package com.example.remiderapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remiderapp.Model.Category
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ViewModel.CategoryViewModel
import com.example.remiderapp.ViewModel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFolder(
            onDimiss:()->Unit,
           Save:(String)->Unit,
            showDialog: Boolean
           )
{
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var input by remember{ mutableStateOf("") }
    val categoryViewModel : CategoryViewModel = viewModel()
    if(showDialog)
    {
        ModalBottomSheet(
            onDismissRequest = onDimiss ,
            sheetState = sheetState
        ) {
            Column(Modifier.padding(16.dp)) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    label = {
                        Text("New Folder")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = onDimiss, modifier = Modifier.padding(end=8.dp)) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        Save(input)
                        val category = Category(0,input)
                        categoryViewModel.addCategory(category)
                        onDimiss()
                    }) {
                        Text("OK")
                    }


                }

            }
        }

    }

}