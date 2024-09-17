package com.example.todolisttutorial_exercicio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolisttutorial_exercicio.databinding.ActivityMainBinding
import com.example.todolisttutorial_exercicio.model.TaskItem
import java.time.LocalTime

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskViewModel = TaskViewModelFactory(TaskItemApp.getDao()).create(TaskViewModel::class.java)
        binding.addTaskButton.setOnClickListener {
            saveAction()
        }

        setRecyclerView()

        val adapter = binding.todoListRecyclerView.adapter

        binding.btnActive.setOnClickListener {
            taskViewModel.sortTaskItems(binding.btnActive.text.toString())
            adapter?.notifyDataSetChanged()
        }

        binding.btnCompleted.setOnClickListener {
            taskViewModel.sortTaskItems(binding.btnCompleted.text.toString())
            adapter?.notifyDataSetChanged()
        }

        binding.btnAll.setOnClickListener {
            taskViewModel.sortTaskItems(binding.btnAll.text.toString())
            adapter?.notifyDataSetChanged()
        }

    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem, taskViewModel).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }

    override fun updateTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem, taskViewModel).show(supportFragmentManager, "newTaskTag")
    }

    private fun saveAction() {
        val name = binding.taskName.text.toString()
        val newTask = TaskItem(name = name,  dueTime = LocalTime.now(), completedDate = false)
        taskViewModel.addTaskItem(newTask)

        binding.taskName.setText("")
    }
}

