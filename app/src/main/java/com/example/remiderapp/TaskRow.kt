package com.example.remiderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remiderapp.Model.Task

@Composable
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
fun TaskRow()
{
    var input by  remember { mutableStateOf("") }
    var check = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                            .width(300.dp)
                            .height(30.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                            .background(color = colorResource(id = R.color.black))
                            .padding(8.dp) // khoảng cách giữa viền và nội dung

        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Checkbox(
                    checked = check.value,
                    onCheckedChange = {check.value = !check.value},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Green
                    )

                )
            }
            Column(modifier = Modifier.weight(6f).fillMaxHeight(),
                verticalArrangement = Arrangement.Center,

            ) {
                TextField(
                    value = input,
                    onValueChange = {input = it},
                    placeholder = {
                        if (input.isEmpty())
                            Text(
                                text = "No title",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start, // or Center if you want centered placeholder
                                color = Color.Gray // optional: customize placeholder color
                            )
                                  },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                    )

                )
            }


        }
}
