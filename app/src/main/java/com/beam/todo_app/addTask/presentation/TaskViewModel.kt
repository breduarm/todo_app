package com.beam.todo_app.addTask.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beam.todo_app.addTask.presentation.model.TaskModel
import javax.inject.Inject

class TaskViewModel @Inject constructor() : ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun manageDialog(show: Boolean) {
        _showDialog.value = show
    }

    fun createTask(task: String) {
        _tasks.add(TaskModel(description = task))
        manageDialog(show = false)
    }

    fun markTask(task: TaskModel) {
        val isSelected = task.selected
        val index = _tasks.indexOf(task)
        _tasks[index] = _tasks[index].copy(selected = !isSelected)
    }

    fun removeTask(task: TaskModel) {
        val taskToRemove = _tasks.first { task.id == it.id }
        _tasks.remove(taskToRemove)
    }
}