package com.dwiki.nodemcu.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dwiki.nodemcu.ui.theme.DarkBlue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogEditInfoCistern(
    setHeightCistern: String,
    setWidthCistern: String,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit = {}
) {
    var height by remember { mutableStateOf(setHeightCistern)}
    var width by remember { mutableStateOf(setWidthCistern)}
    var heightIsError by remember { mutableStateOf(false)}
    var widthIsError by remember { mutableStateOf(false)}

    val valuePattern = Regex("^[0-9]+\$")

    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ){
                Text(
                    text = "Perbarui",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = height,
                    onValueChange = {
                        heightIsError = it.isEmpty() || it.isBlank() || !valuePattern.matches(it)
                        height = it
                    },
                    label = {
                        Text(text = "Tinggi")
                    },
                    shape = RoundedCornerShape(5.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DarkBlue,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        errorTextColor = Color.Red,
                        focusedLabelColor = DarkBlue
                    ),
                    trailingIcon = {
                        Text(
                            text = "CM",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    isError = heightIsError,
                    modifier = Modifier
                        .width(120.dp),
                )
                OutlinedTextField(
                    value = width,
                    onValueChange = {
                        widthIsError = it.isEmpty() || it.isBlank() || !valuePattern.matches(it)
                        width = it
                    },
                    label = {
                        Text(text = "Lebar")
                    },
                    shape = RoundedCornerShape(5.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors =  OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DarkBlue,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        errorTextColor = Color.Red,
                        focusedLabelColor = DarkBlue
                    ),
                    trailingIcon = {
                        Text(
                            text = "CM",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    isError = widthIsError,
                    modifier = Modifier
                        .width(120.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            onConfirm.invoke(height, width)
                        },
                        enabled = !heightIsError && !widthIsError,
                        modifier = Modifier
                            .width(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBlue,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(text = "SIMPAN", color = Color.White)
                    }
                    Button(
                        onClick = {
                            onDismiss.invoke()
                        },
                        modifier = Modifier
                            .width(100.dp),
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(1.dp, DarkBlue),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text(text = "BATAL")
                    }
                }
            }
        }
    }
}