package com.wind.tv.android.util

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.shared.util.Parcelable

const val DATA = "data"

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.getExtra() = if (AndroidVersionUtil.hasTiramisu()) {
    getParcelableExtra(DATA, T::class.java)
} else {
    getParcelableExtra<T>(DATA)
}!!

inline fun <reified T : ComponentActivity> Context.startActivity(data: Parcelable? = null) {
    Intent(this, T::class.java).apply {
        data?.let {
            putExtra(DATA, data)
        }
        startActivity(this)
    }
}
