package com.margsapp.todo.listtasks

import androidx.lifecycle.ViewModel
import com.margsapp.todo.TaskRepository
import com.margsapp.todo.models.Task

class ListFragmentViewModel: ViewModel() {
    private val taskRepository = TaskRepository.get()

    val taskList = taskRepository.getTasks()

    var taskListSize = taskRepository.getCount()

    fun deleteItem(task: Task){
        taskRepository.deleteTask(task)
    }

}