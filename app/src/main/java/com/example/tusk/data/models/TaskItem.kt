package com.example.tusk.data.models

import java.sql.Time

data class TaskItem(
    val id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var time: Time,
    var forToday: Boolean = true,
    var isCompleted: Boolean = false
)
