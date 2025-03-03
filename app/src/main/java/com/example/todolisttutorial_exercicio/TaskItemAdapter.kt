package com.example.todolisttutorial_exercicio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolisttutorial_exercicio.databinding.TaskItemCellBinding
import com.example.todolisttutorial_exercicio.model.TaskItem

class TaskItemAdapter(
    private val taskItems: List<TaskItem>,
    private val clickListener: TaskItemClickListener

): RecyclerView.Adapter<TaskItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    override fun getItemCount(): Int = taskItems.size

}