package com.example.todolisttutorial_exercicio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolisttutorial_exercicio.model.TaskItem
import com.example.todolisttutorial_exercicio.persistence.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID

class TaskViewModel(
    private val db: TaskDao,
): ViewModel() {

    var taskItems = MutableLiveData<MutableList<TaskItem>>()
    private var taskItemsBackup = MutableLiveData<MutableList<TaskItem>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            taskItems.postValue(db.getAllTasks().toMutableList())
            taskItemsBackup.postValue(db.getAllTasks().toMutableList())
        }
    }

    fun addTaskItem(taskItem: TaskItem) {
        val list = taskItems.value
        list!!.add(taskItem)
        taskItems.postValue(list!!)
        //taskItemsBackup.postValue(list!!)
        viewModelScope.launch(Dispatchers.IO) {
            db.insertTask(taskItem)
        }

    }

    fun updateTaskItem(id: UUID, name: String, dueTime: LocalTime?) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.dueTime = dueTime
        taskItems.postValue(list!!)
        //taskItemsBackup.postValue(list!!)

        viewModelScope.launch(Dispatchers.IO) {
            db.updateTask(task)
        }
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        val list = taskItems.value

        if (list!!.isEmpty()) return

        val task = list.find { it.id == taskItem.id}!!


        list.remove(task)
        taskItems.postValue(list!!)
        //taskItemsBackup.postValue(list!!)

        viewModelScope.launch(Dispatchers.IO) {
            db.deleteTask(taskItem)
        }
    }

    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id}!!
        task.completedDate = true
        taskItems.postValue(list!!)
        //taskItemsBackup.postValue(list!!)

        viewModelScope.launch(Dispatchers.IO) {
            db.updateTask(task)
        }
    }

    fun sortTaskItems(nameButton: String) {

        viewModelScope.launch(Dispatchers.IO) {
            taskItemsBackup.postValue(db.getAllTasks().toMutableList())
        }

        if (nameButton == "Completed") {
            val list = taskItemsBackup.value
            val task = list!!.filter { it.completedDate }
            taskItems.postValue(task.toMutableList())
        }

        else if (nameButton == "Active"){
            val list = taskItemsBackup.value
            val task = list!!.filterNot { it.completedDate }
            taskItems.postValue(task.toMutableList())
        }

        else if (nameButton == "All"){
            //val list = taskItemsBackup.value
            //taskItems.postValue(list)
            /*viewModelScope.launch(Dispatchers.IO) {
                taskItems.postValue(db.getAllTasks().toMutableList())
                taskItemsBackup.postValue(db.getAllTasks().toMutableList())
            }*/

            taskItems.postValue(taskItemsBackup.value)
        }

    }
}

class TaskViewModelFactory(
    private val db: TaskDao,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(
            db = db
        ) as T
    }
}
