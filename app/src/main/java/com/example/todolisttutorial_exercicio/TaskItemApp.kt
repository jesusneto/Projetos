package com.example.todolisttutorial_exercicio

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.room.Room
import com.example.todolisttutorial_exercicio.persistence.TaskDao
import com.example.todolisttutorial_exercicio.persistence.TasksDatabase

class TaskItemApp : Application() {
    private var db: TasksDatabase? = null

    init {
        instance = this
    }

    private fun getDb() : TasksDatabase {
        if (db != null) {
            return db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                TasksDatabase::class.java, "task_items"
            ).fallbackToDestructiveMigration().build()

            return db!!
        }
    }

    companion object {
        private var instance: TaskItemApp? = null

        fun getDao() : TaskDao {
            return instance!!.getDb().taskDao()
        }

        fun getUriPermission(uri: Uri) {
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
}