package com.margsapp.todo.edittask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.margsapp.todo.TaskRepository
import com.margsapp.todo.models.Task
import java.util.*

class EditFragmentViewModel: ViewModel() {
    private val taskRepository = TaskRepository.get()
    private val taskIdLiveData = MutableLiveData<UUID>()

    var taskLiveData: LiveData<Task?> =
        Transformations.switchMap(taskIdLiveData) { taskId ->
            taskRepository.getTaskFromId(taskId)
        }

    fun loadTask(taskId: UUID) {
        taskIdLiveData.value = taskId
    }

    fun saveTask(task: Task) {
        taskRepository.updateTask(task)
    }

}