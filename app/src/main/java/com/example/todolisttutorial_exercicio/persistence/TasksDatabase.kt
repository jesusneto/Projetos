package com.example.todolisttutorial_exercicio.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolisttutorial_exercicio.Converters
import com.example.todolisttutorial_exercicio.model.TaskItem

@Database(entities = [TaskItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}