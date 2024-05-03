package com.beam.todo_app.addTask.domain

import com.beam.todo_app.addTask.data.TaskRepository
import com.beam.todo_app.addTask.presentation.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: TaskModel) {
        taskRepository.delete(task)
    }
}