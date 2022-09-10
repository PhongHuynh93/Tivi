package com.shared.myapplication

import com.squareup.sqldelight.ColumnAdapter

val intAdapter = object : ColumnAdapter<List<Int>, String> {

    override fun encode(value: List<Int>) = value.joinToString(separator = ",")

    override fun decode(databaseValue: String): List<Int> =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",").map { it.toInt() }
        }
}

val stringAdapter = object : ColumnAdapter<List<String>, String> {

    override fun encode(value: List<String>) = value.joinToString(separator = ",")

    override fun decode(databaseValue: String): List<String> =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",").map { it }
        }
}
