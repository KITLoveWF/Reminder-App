package com.example.remiderapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remiderapp.Model.Task

@Composable
fun TaskDate(tasks: List<Task>?, onDismiss: () -> Unit,)
{
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text("Nhiệm vụ")
        },
        text = {

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    tasks?.forEach { task ->
                        Text("• ${task.title} (${task.time})", fontSize = 14.sp, modifier = Modifier.padding(2.dp))
                    }
                }
        }
    )
}