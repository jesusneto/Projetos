package com.example.todolisttutorial_exercicio

import com.example.todolisttutorial_exercicio.model.TaskItem

interface TaskItemClickListener {
    fun editTaskItem(taskItem: TaskItem)

    fun completeTaskItem(taskItem: TaskItem)

    fun deleteTaskItem(taskItem: TaskItem)

    fun updateTaskItem(taskItem: TaskItem)
}
