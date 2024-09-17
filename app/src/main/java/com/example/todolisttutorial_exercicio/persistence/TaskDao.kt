package com.example.todolisttutorial_exercicio.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolisttutorial_exercicio.model.TaskItem
import java.util.UUID

@Dao
interface TaskDao {
    @Insert
    fun insertTask(taskItem: TaskItem)

    @Update
    fun updateTask(taskItem: TaskItem)

    @Delete
    fun deleteTask(taskItem: TaskItem)

    @Query("SELECT * FROM task_items WHERE id = :id")
    suspend fun getTaskById(id: UUID): TaskItem?

    @Query("SELECT * FROM task_items")
    suspend fun getAllTasks(): List<TaskItem>
}