package com.dwiki.nodemcu.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    message: String,
    action: () -> Unit,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Snackbar(
            shape = RectangleShape,
            containerColor = Color.Red,
            contentColor = Color.White,
            action = {
                if (actionLabel != null) {
                    Text(
                        modifier = Modifier
                            .clickable { action() }
                            .padding(end = 12.dp),
                        text = actionLabel,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                }
            },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "icon snackbar",
                    tint = Color.White,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                )
            }
        }
    }
}