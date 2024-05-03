package com.beam.todo_app.addTask.presentation.model

data class TaskModel(
    val id: Long = System.currentTimeMillis(),
    val description: String,
    val selected: Boolean = false,
)
