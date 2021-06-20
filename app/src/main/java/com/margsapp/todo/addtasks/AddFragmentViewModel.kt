package com.margsapp.todo.addtasks

import androidx.lifecycle.ViewModel
import com.margsapp.todo.TaskRepository
import com.margsapp.todo.models.Task

class AddFragmentViewModel: ViewModel() {
    var currentTask: Task = Task()

    private val taskRepository = TaskRepository.get()

    fun addTask(task: Task){
        taskRepository.addTask(task)
    }

}