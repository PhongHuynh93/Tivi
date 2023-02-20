package com.wind.tv.android.util

import android.os.Build

object AndroidVersionUtil {
    fun hasGB(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
    }

    fun hasHC(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
    }

    fun hasHCMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
    }

    fun hasHCMR2(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2
    }

    fun hasICS(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
    }

    fun hasICSMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
    }

    fun hasJB(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    fun hasJBMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    fun hasJBMR2(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    val isKitKat: Boolean
        get() = Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT

    fun hasKitKat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun hasLollipopMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    val isMarshmallow: Boolean
        get() = Build.VERSION.SDK_INT == Build.VERSION_CODES.M

    fun hasMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    val isNougat: Boolean
        get() = Build.VERSION.SDK_INT == Build.VERSION_CODES.N

    fun hasNougat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    fun hasOreo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    fun hasOreoMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
    }

    fun hasPie(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    fun hasQ(): Boolean {
        return Build.VERSION.SDK_INT >= 29 // hard-code 29 because of the target SDK
    }

    fun hasTiramisu(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
}
