package com.beam.todo_app.addTask.domain

import com.beam.todo_app.addTask.data.TaskRepository
import com.beam.todo_app.addTask.presentation.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}