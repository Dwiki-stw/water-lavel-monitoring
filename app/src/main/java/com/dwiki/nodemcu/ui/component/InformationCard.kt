package com.dwiki.nodemcu.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiki.nodemcu.ui.theme.DarkBlue

@Composable
fun InformationCard(
    title: String,
    icon: Painter,
    colorIcon: Color = Color.Black,
    colorBackgroundIcon: Color = Color.White,
    listFillContent: List<String>,
    listContent: List<String>,
    onClickEnable: Boolean = false,
    onClick: () -> Unit = {}

) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 20.dp)
            .clickable(onClick = onClick, enabled = onClickEnable)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = colorIcon,
                modifier = Modifier
                    .background(colorBackgroundIcon, RoundedCornerShape(2.dp))
                    .padding(horizontal = 10.dp)
                    .width(60.dp)
                    .height(122.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )
                    if (onClickEnable){
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(15.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Column{
                        Text(
                            text = listFillContent[0],
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                        Text(
                            text = listFillContent[1],
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                        Text(
                            text = listFillContent[2],
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = ": ${listContent[0]}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                        Text(
                            text = ": ${listContent[1]}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                        Text(
                            text = ": ${listContent[2]}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
