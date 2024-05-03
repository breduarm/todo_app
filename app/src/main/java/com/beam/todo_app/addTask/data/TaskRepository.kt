package com.beam.todo_app.addTask.data

import com.beam.todo_app.addTask.presentation.model.TaskModel
import com.beam.todo_app.addTask.presentation.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    val tasks: Flow<List<TaskModel>> = taskDao.getTask().map { entityList ->
        entityList.map { it.toDomain() }
    }
    suspend fun create(task: TaskModel) {
        taskDao.createTask(task.toEntity())
    }

    suspend fun update(task: TaskModel) {
        taskDao.updateTask(task.toEntity())
    }
}