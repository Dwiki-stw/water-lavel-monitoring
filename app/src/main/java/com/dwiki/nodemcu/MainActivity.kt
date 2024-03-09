package com.dwiki.nodemcu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dwiki.nodemcu.ui.theme.NodemcuTheme
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import com.dwiki.nodemcu.data.Repository
import com.dwiki.nodemcu.ui.screen.SplashScreen
import com.dwiki.nodemcu.ui.screen.monitoring.MonitoringScreen
import com.dwiki.nodemcu.ui.screen.monitoring.MonitoringViewModel
import com.dwiki.nodemcu.utils.ConnectivityObserver
import com.dwiki.nodemcu.utils.NodemcuConnectivityObserver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            connectivityObserver = NodemcuConnectivityObserver(applicationContext)
            val myRepository = Repository(applicationContext)
            val myViewModel = MonitoringViewModel(myRepository)
            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Disconnected
            )
            NodemcuTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MonitoringApp(status, myViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetIp(
    value:String,
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ){

        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it)},
            label = {
                Text(text = "IP Address")
            },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Black
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Hubungkan")
        }

    }
}