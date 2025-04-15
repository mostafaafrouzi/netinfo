package com.example.netinfo

import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.netinfo.ui.theme.NetInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkMode = isSystemInDarkTheme()
            NetInfoTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var deviceInfo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val telephonyManager = context.getSystemService(TelephonyManager::class.java)
        deviceInfo = "Device: ${android.os.Build.MANUFACTURER}\nAndroid: ${android.os.Build.VERSION.RELEASE}"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                // Vibrate on click
                val vibrator = context.getSystemService(Vibrator::class.java)
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                
                try {
                    // Try to open phone info directly
                    val intent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    try {
                        // Fallback method 1
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.setClassName(
                            "com.android.settings",
                            "com.android.settings.RadioInfo"
                        )
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        try {
                            // Fallback method 2 - Using Accessibility
                            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "دستگاه شما از این قابلیت پشتیبانی نمی‌کند",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text("باز کردن اطلاعات گوشی")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = deviceInfo,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.Start)
        )
    }
} 