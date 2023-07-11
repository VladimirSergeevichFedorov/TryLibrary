package com.example.newtrykmmlibrary

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class AndroidPlatform : Platform {
    override val name: String = "Android Neoflex ${android.os.Build.VERSION.SDK_INT}"
}

class ToolanoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            IFromLib()
        }
    }
}
@Composable
fun IFromLib(){
    val r = remember {
        mutableStateOf(true)

    }
    Text(text = "Hi Tylano ${r.value}")
}

actual fun getPlatform(): Platform = AndroidPlatform()