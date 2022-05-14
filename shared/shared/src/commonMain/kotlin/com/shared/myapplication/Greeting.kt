package com.shared.myapplication

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}