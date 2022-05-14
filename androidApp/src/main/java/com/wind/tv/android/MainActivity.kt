package com.wind.tv.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shared.myapplication.Greeting
import android.widget.TextView
import com.shared.myapplication.android.R

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
    }
}
