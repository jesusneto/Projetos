package com.example.todolisttutorial_exercicio

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolisttutorial_exercicio.databinding.FragmentNewTaskSheetBinding
import com.example.todolisttutorial_exercicio.model.TaskItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheet(
    var taskItem: TaskItem?,
    var taskViewModel: TaskViewModel)
    : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    //private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val activity = requireActivity()

        if (taskItem != null) {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)

        }

        //taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.name.text.toString()

        if (taskItem == null) {
            //val newTask = TaskItem(name, desc, dueTime, null)
            val newTask = TaskItem(name = name,  dueTime = LocalTime.now(), completedDate = false)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskViewModel.updateTaskItem(taskItem!!.id, name, LocalTime.now())
        }
        binding.name.setText("")
        dismiss()
    }

}