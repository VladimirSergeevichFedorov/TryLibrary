package com.example.newtrykmmlibrary

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentActivity

class AndroidPlatform : Platform {
    override val name: String = "Android Neoflex ${android.os.Build.VERSION.SDK_INT}"
}

class ToolanoActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize().background(Color.Blue)) {
                IFromLib()
            }

        }
    }
}
@Composable
fun IFromLib(){
    val r = remember {
        mutableStateOf(true)

    }
    Text(modifier = Modifier.background(Color.Black),text = "Hi Tylano")
}

actual fun getPlatform(): Platform = AndroidPlatform()