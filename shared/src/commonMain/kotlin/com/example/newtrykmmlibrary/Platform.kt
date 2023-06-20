package com.example.newtrykmmlibrary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform