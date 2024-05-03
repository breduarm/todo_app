package com.beam.todo_app.addTask.presentation

import com.beam.todo_app.addTask.presentation.model.TaskModel

sealed interface TaskUiState {
    data object Loading: TaskUiState
    data class Error(val throwable: Throwable): TaskUiState
    data class Success(val tasks: List<TaskModel>) : TaskUiState
}