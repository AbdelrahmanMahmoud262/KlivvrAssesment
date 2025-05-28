package com.klivvrassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.klivvrassesment.ui.navigation.MainNavHost
import com.klivvrassesment.ui.theme.KlivvrAssesmentTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KlivvrAssesmentTheme {
                KoinContext {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val navController = rememberNavController()
                        MainNavHost(
                            modifier = Modifier
                                .padding(innerPadding),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}