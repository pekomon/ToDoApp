package com.example.pekomon.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pekomon.todoapp.util.Consts.TODO_TABLE_NAME

@Entity(tableName = TODO_TABLE_NAME)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)