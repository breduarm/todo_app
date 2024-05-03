package com.beam.todo_app.addTask.presentation.model

import com.beam.todo_app.addTask.data.TaskEntity

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val description: String,
    val selected: Boolean = false,
)

fun TaskModel.toEntity(): TaskEntity =
    TaskEntity(
        id = id,
        description = description,
        selected = selected,
    )
