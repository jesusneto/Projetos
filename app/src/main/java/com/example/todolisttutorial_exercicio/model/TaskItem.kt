package com.example.todolisttutorial_exercicio.model

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.todolisttutorial_exercicio.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "task_items")
data class TaskItem(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var name: String,
    var dueTime: LocalTime?,
    var completedDate: Boolean,
) {

    fun isCompleted() = completedDate
    fun imageResource(): Int = if (isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24
    fun imageColor(context: Context): Int = if (isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}