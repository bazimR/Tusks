package com.example.tusk.data.models


data class TaskItem(
    val id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var time: String = "",
    var forToday: Boolean = true,
    var isCompleted: Boolean = false
)
