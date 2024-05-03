package com.beam.todo_app.addTask.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beam.todo_app.addTask.domain.AddTasksUseCase
import com.beam.todo_app.addTask.domain.GetTasksUseCase
import com.beam.todo_app.addTask.domain.UpdateTasksUseCase
import com.beam.todo_app.addTask.presentation.TaskUiState.Success
import com.beam.todo_app.addTask.presentation.TaskUiState.Error
import com.beam.todo_app.addTask.presentation.TaskUiState.Loading
import com.beam.todo_app.addTask.presentation.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val addTasksUseCase: AddTasksUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase,
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> = getTasksUseCase()
        .map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

//    private val _tasks = mutableStateListOf<TaskModel>()
//    val tasks: List<TaskModel> = _tasks

    fun manageDialog(show: Boolean) {
        _showDialog.value = show
    }

    fun createTask(task: String) {
        manageDialog(show = false)
        viewModelScope.launch {
            addTasksUseCase(TaskModel(description = task))
        }
    }

    fun markTask(task: TaskModel) {
        viewModelScope.launch {
            updateTasksUseCase(task.copy(selected = !task.selected))
        }
    }

    fun removeTask(task: TaskModel) {
//        val taskToRemove = _tasks.first { task.id == it.id }
//        _tasks.remove(taskToRemove)
    }
}