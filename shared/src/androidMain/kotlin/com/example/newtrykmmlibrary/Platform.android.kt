package com.example.newtrykmmlibrary

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class AndroidPlatform : Platform {
    override val name: String = "Android Neoflex ${android.os.Build.VERSION.SDK_INT}"
}

@Composable
fun IFromLib(){
    Text(text = "Hi Tylano")
}

actual fun getPlatform(): Platform = AndroidPlatform()