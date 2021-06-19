package com.margs.todo.listtasks

import androidx.lifecycle.ViewModel
import com.margs.todo.TaskRepository
import com.margs.todo.models.Task

class ListFragmentViewModel: ViewModel() {
    private val taskRepository = TaskRepository.get()

    val taskList = taskRepository.getTasks()

    var taskListSize = taskRepository.getCount()

    fun deleteItem(task: Task){
        taskRepository.deleteTask(task)
    }

}