package com.personal.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.personal.androidapp.data.repository.DownloadRepository
import com.personal.androidapp.ui.navigation.AppNavHost
import com.personal.androidapp.ui.theme.PersonalAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = DownloadRepository(applicationContext)

        setContent {
            PersonalAppTheme {
                AppNavHost(repository = repository)
            }
        }
    }
}
