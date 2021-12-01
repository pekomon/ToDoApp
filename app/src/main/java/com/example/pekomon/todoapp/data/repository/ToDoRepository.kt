package com.example.pekomon.todoapp.data.repository

import com.example.pekomon.todoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    val getAllTasks: Flow<List<ToDoTask>>
    val getAllByLowPriority: Flow<List<ToDoTask>>
    val getAllByHighPriority: Flow<List<ToDoTask>>
    fun getTask(id: Int): Flow<ToDoTask>
    suspend fun addTask(task: ToDoTask)
    suspend fun updateTask(task: ToDoTask)
    suspend fun deleteTask(task: ToDoTask)
    suspend fun deleteAll()
    fun search(query: String): Flow<List<ToDoTask>>

























}