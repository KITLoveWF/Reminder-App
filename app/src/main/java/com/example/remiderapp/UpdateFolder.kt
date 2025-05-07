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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Update
import com.example.remiderapp.Model.Category
import com.example.remiderapp.ViewModel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFolder(
    onDimiss:()->Unit,
    Save:(String)->Unit,
    showDialog: Boolean,
    category: Category
)
{
    val sheetState = rememberModalBottomSheetState()
    //val scope = rememberCoroutineScope()
    var input by remember{ mutableStateOf(category.name) }
    val categoryViewModel: CategoryViewModel = viewModel()
    val _folders = categoryViewModel.getCategory().observeAsState(emptyList())
    val folders = _folders.value
    if(showDialog)
    {
        ModalBottomSheet(
            onDismissRequest = onDimiss ,
            sheetState = sheetState
        ) {
            Column(Modifier.padding(16.dp)) {
                TextField(
                    value = input,
                    onValueChange = {
                        input = it
                                    },
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
                        val updatedCategory = category.copy(name = input)
                        folders.map {
                            if (it.id == category.id)
                                categoryViewModel.updateCategory(updatedCategory)
                            else it
                        }
                        //Save(input)
                        onDimiss()
                    }) {
                        Text("OK")
                    }


                }

            }
        }

    }

}