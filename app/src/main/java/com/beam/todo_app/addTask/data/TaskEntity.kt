package com.beam.todo_app.addTask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beam.todo_app.addTask.presentation.model.TaskModel

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val description: String,
    val selected: Boolean = false,
)

fun TaskEntity.toDomain(): TaskModel =
    TaskModel(
        id = id,
        description = description,
        selected = selected,
    )
