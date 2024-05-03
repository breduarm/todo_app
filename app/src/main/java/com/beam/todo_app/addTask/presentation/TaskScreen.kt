package com.beam.todo_app.addTask.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.beam.todo_app.addTask.presentation.TaskUiState.Error
import com.beam.todo_app.addTask.presentation.TaskUiState.Loading
import com.beam.todo_app.addTask.presentation.TaskUiState.Success
import com.beam.todo_app.addTask.presentation.model.TaskModel

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val showDialog: Boolean by viewModel.showDialog.observeAsState(initial = false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TaskUiState>(
        initialValue = Loading,
        key1 = lifecycle,
        key2 = viewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when(uiState) {
        Loading -> {
            CircularProgressIndicator()
        }
        is Error -> TODO()
        is Success -> {
            val currentUiState = uiState as Success
            TaskScreenContent(
                tasks = currentUiState.tasks,
                showDialog = showDialog,
                manageDialog = { viewModel.manageDialog(it) },
                createTask = { viewModel.createTask(it) },
                markTask = { viewModel.markTask(it) },
                removeTask = { viewModel.removeTask(it) },
            )
        }
    }
}

@Composable
fun TaskScreenContent(
    tasks: List<TaskModel>,
    showDialog: Boolean,
    manageDialog: (Boolean) -> Unit,
    createTask: (String) -> Unit,
    markTask: (TaskModel) -> Unit,
    removeTask: (TaskModel) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        AddTaskDialog(
            showDialog,
            onDismiss = { manageDialog(false) },
            onAddTask = { createTask(it) }
        )
        TaskList(tasks, markTask, removeTask)
        FABDialog(modifier = Modifier.align(Alignment.BottomEnd)) {
            manageDialog(true)
        }
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onAddTask: (String) -> Unit) {
    var task by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Add your task",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = task,
                    onValueChange = { task = it },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onAddTask(task)
                        task = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add task")
                }
            }
        }
    }
}

@Composable
fun FABDialog(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(24.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task")
    }
}

@Composable
fun TaskList(
    tasks: List<TaskModel>,
    markTask: (TaskModel) -> Unit,
    removeTask: (TaskModel) -> Unit,
) {
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            TaskItem(task = task, onCheckedChange = markTask, onLongPress = removeTask)
        }
    }
}

@Composable
fun TaskItem(
    task: TaskModel,
    onCheckedChange: (TaskModel) -> Unit,
    onLongPress: (TaskModel) -> Unit,
) {
    Column(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onLongPress = { onLongPress(task) })
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = task.description, modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            )
            Checkbox(checked = task.selected, onCheckedChange = { onCheckedChange(task) })
        }
        Divider(modifier = Modifier.height(1.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreenContent(
        tasks = listOf(TaskModel(description = "Task 1")),
        showDialog = false,
        manageDialog = {},
        createTask = {},
        markTask = {},
        removeTask = {},
    )
}