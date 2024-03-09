package com.dwiki.nodemcu.ui.screen.monitoring

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiki.nodemcu.R
import com.dwiki.nodemcu.data.local.model.InfoCisternModel
import com.dwiki.nodemcu.data.remote.response.MonitoringResponse
import com.dwiki.nodemcu.ui.component.DialogEditInfoCistern
import com.dwiki.nodemcu.ui.component.InformationCard
import com.dwiki.nodemcu.ui.component.PercentageWaterCapacity
import com.dwiki.nodemcu.ui.component.CustomSnackbar
import com.dwiki.nodemcu.ui.component.WavesLoadingIndicator
import com.dwiki.nodemcu.ui.theme.BlueTurquoise
import com.dwiki.nodemcu.ui.theme.DarkBlue
import com.dwiki.nodemcu.utils.ConnectivityObserver
import com.dwiki.nodemcu.utils.Resource
import com.dwiki.nodemcu.utils.provideDateNow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


@Composable
fun MonitoringScreen(
    status: ConnectivityObserver.Status,
    monitoringVM: MonitoringViewModel,
) {
    var updateData by remember { mutableIntStateOf(0) }
    val dataMonitoringFlow by monitoringVM.dataMonitoringFlow.collectAsState()
    val postHeightResponseFlow by monitoringVM.postHeightResponseFlow.collectAsState()
    var dataMonitoring by remember { mutableStateOf(MonitoringResponse())}
    var isDialogEditInfoOpen by remember { mutableStateOf(false )}
    var showSnackbar by remember { mutableStateOf(false)}
    var actionLabel by remember { mutableStateOf("")}
    var message by remember { mutableStateOf("") }

    val infoCistern = monitoringVM.getInfoCistern()
    var saveHeight by remember { mutableStateOf("")}
    var saveWidth by remember { mutableStateOf("")}
    var savePumpState by remember { mutableStateOf(false)}

    val scope = rememberCoroutineScope()

    LaunchedEffect(status){
        if (status == ConnectivityObserver.Status.Connected){
            Log.d("MonitoringScreen", "Get Data Started $updateData")
            monitoringVM.getDataMonitoring()
            updateData++
            delay(1000L)
        }
    }

    dataMonitoringFlow?.let {
        when(it) {
            is Resource.Success -> {
                if (it.data != null){
                    dataMonitoring = it.data
                }
                Log.d("MonitoringScreen", "Resource: Success")
                scope.launch {
                    if (status == ConnectivityObserver.Status.Connected){
                        Log.d("MonitoringScreen", "Get Data Started $updateData")
                        delay(1000L)
                        monitoringVM.getDataMonitoring()
                        updateData++
                    }

                }
            }
            is Resource.Loading -> {
                Log.d("MonitoringScreen", "Resource: Loading")
            }
            is Resource.Error -> {
                actionLabel = "Refresh"
                message = it.message
                showSnackbar = true
                Log.d("MonitoringScreen", "Resource: Error")
            }
        }
    }

    postHeightResponseFlow?.let {
        when(it){
            is Resource.Success -> {
                LaunchedEffect(postHeightResponseFlow) {
                    saveInfoCistern(saveHeight, saveWidth, monitoringVM)
                }
                Log.d("MonitoringScreen", "Post Response: Success")
            }
            is Resource.Loading -> {
                Log.d("MonitoringScreen", "Post Response: Loading")
            }
            is Resource.Error -> {
                Log.d("MonitoringScreen", "Post Response: Error")
                LaunchedEffect(postHeightResponseFlow){
                    actionLabel = "OK"
                    message = it.message
                    showSnackbar = true
                }

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .background(BlueTurquoise)
        ) {
            WavesLoadingIndicator(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxSize(),
                color = DarkBlue,
                progress = percentageToFloat(dataMonitoring.waterCapacity)
            )
            TopSection(status = status)
            PercentageWaterCapacity(percentage = dataMonitoring.waterCapacity)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp)
        ) {
            Text(
                text = "Informasi",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))
            InformationCard(
                title = "Tangki air",
                icon = painterResource(id = R.drawable.cistern),
                colorIcon = Color.White,
                colorBackgroundIcon = BlueTurquoise,
                listFillContent = listOf("Tinggi", "Lebar", "Kapasitas"),
                listContent = listOf(
                    "${infoCistern.height} CM",
                    "${infoCistern.width} CM",
                    "${infoCistern.capacity} Liter"
                ),
                onClickEnable = true
            ) {
                isDialogEditInfoOpen = true
            }
            InformationCard(
                title = "Pompa air",
                icon = painterResource(id = R.drawable.pump),
                colorIcon = Color.White,
                colorBackgroundIcon = BlueTurquoise,
                listFillContent = listOf("Status", "Durasi", "Terakhir"),
//                listContent = listOf("OFF", "10 Menit", "10/12/2023, 15:00"),
                listContent = infoPump(
                    pumpStateNow = dataMonitoring.statusPump,
                    duration = "${dataMonitoring.duration} Menit",
                    monitoringVM = monitoringVM,
                    launchEffect = { pumpStateNow ->
                        Log.d("Info Pump", "savePumpState : $savePumpState")
                        if (pumpStateNow){
                            savePumpState = true
                            Log.d("Info Pump", "savePumpState update : $savePumpState")
                        }
                        if (!pumpStateNow && savePumpState) {
                            val date = provideDateNow()
                            Log.d("Info Pump", "Date : $date")
                            monitoringVM.setLastFill(date)
                            savePumpState = false
                        }
                    }
                )
            )
        }
    }
    if (showSnackbar){
        CustomSnackbar(
            actionLabel = actionLabel,
            message = message
        ) {
            if (actionLabel != "OK"){
                onRefresh(monitoringVM)
            }
            showSnackbar = false
        }
    }

    if (isDialogEditInfoOpen){
        DialogEditInfoCistern(
            setHeightCistern = infoCistern.height ?: "",
            setWidthCistern = infoCistern.width ?: "",
            onDismiss = {
                isDialogEditInfoOpen = false
            },
            onConfirm = { height, width ->
                monitoringVM.postHeightCistern(height.toInt())
                saveHeight = height
                saveWidth = width
                isDialogEditInfoOpen = false
            }
        )
    }

}

@Composable
fun TopSection(
    status: ConnectivityObserver.Status
) {
    var icon: ImageVector? = null
    var text = ""
    var color = Color.White

    when(status){
        ConnectivityObserver.Status.Connected -> {
            icon = Icons.Default.Wifi
            text = "Connected"
            color = Color.White
        }
        ConnectivityObserver.Status.Disconnected -> {
            icon = Icons.Default.SignalWifiOff
            text = "Disconnected"
            color = Color.Red
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color
        )

        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            color = color
        )
    }
}

fun percentageToFloat(waterCapacity: Int) = (waterCapacity/100.0).toFloat()

private fun onRefresh(monitoringVM: MonitoringViewModel) {
    Log.d("MonitoringScreen", "On Refresh")
    monitoringVM.getDataMonitoring()
}

private fun saveInfoCistern(height: String, width: String, monitoringVM: MonitoringViewModel) {
    val capacity = calculateCisternCapacity(height, width)
    val model = InfoCisternModel(height, width, capacity)

    monitoringVM.setInfoCistern(model)
    Log.d("MonitoringScreen", "saveInfoCistern: $capacity")
}

private fun calculateCisternCapacity(height: String, width: String) : String {
    val radius = width.toDouble() / 2
    val height = height.toDouble()

    val volume = (radius*radius*height*3.14).toInt()

    Log.d("MonitoringScreen", "calculateCisternCapacity: $radius $height $volume")

    return (volume/1000).toString()
}

private fun infoPump(pumpStateNow: Boolean, duration: String, launchEffect: (Boolean) -> Unit, monitoringVM: MonitoringViewModel) : List<String> {
    launchEffect(pumpStateNow)

    val pumpCondition = if (pumpStateNow) "ON" else "OFF"
    val lastFill = monitoringVM.getLastFill() ?: ""


    return listOf(pumpCondition, duration, lastFill)
}