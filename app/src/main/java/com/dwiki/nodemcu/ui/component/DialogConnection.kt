package com.dwiki.nodemcu.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dwiki.nodemcu.ui.theme.DarkBlue

@Composable
fun DialogConnection(
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: () -> Unit = {},
    statusWifi: String = "Disconnect",
    stateNodeMcu: String = "",
    ipAddress: String = ""
) {

    val text1 = "Pastikan perangkat Anda terhubung dengan WiFi"
    val text2 = "Pastikan perangkat NodeMcu terhubung dengan WiFi yang sama"

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 5.dp)
            ) {
                Text(
                    text = "Koneksi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(15.dp))
                    InfoItem(fillItem = "WiFi", valueItem = "Disconnect")
                    InfoItem(fillItem = "NodeMcu", valueItem = "Disconnect")
                    InfoItem(fillItem = "IP Address", valueItem = "-")
                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = "",
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Light,
//                    color = Color.Red,
//                    textAlign = TextAlign.Left,
//                    modifier = Modifier.fillMaxWidth()
//                )
            }
        }
    }
}

@Composable
fun InfoItem(
    fillItem: String,
    valueItem: String = "-",
) {
    val conditionValue = valueItem == "Disconnect"
    val backgroundColor = if (conditionValue) Color.Red else Color.White
    val fontColor = if (conditionValue) Color.White else Color.Black

    Row(
        modifier = Modifier.padding(bottom = 2.dp)
    ) {
        Text(
            text = fillItem,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.width(80.dp)
        )
        Text(text = ": ")
        Text(
            text = valueItem,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = fontColor,
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(2.dp))
                .padding(horizontal = 5.dp)
        )
    }
}

@Preview
@Composable
fun Preview3() {
    DialogConnection()
}