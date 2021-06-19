package com.margs.todo.addtasks

import androidx.lifecycle.ViewModel
import com.margs.todo.TaskRepository
import com.margs.todo.models.Task

class AddFragmentViewModel: ViewModel() {
    var currentTask: Task = Task()

    private val taskRepository = TaskRepository.get()

    fun addTask(task: Task){
        taskRepository.addTask(task)
    }

}