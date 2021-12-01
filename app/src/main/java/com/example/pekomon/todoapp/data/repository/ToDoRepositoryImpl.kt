package com.example.pekomon.todoapp.data.repository

import com.example.pekomon.todoapp.data.db.ToDoDao
import com.example.pekomon.todoapp.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepositoryImpl @Inject constructor(
    private val dao: ToDoDao
) : ToDoRepository {
    override val getAllTasks: Flow<List<ToDoTask>> = dao.getAll()
    override val getAllByLowPriority: Flow<List<ToDoTask>> = dao.getAllByLowPriority()
    override val getAllByHighPriority: Flow<List<ToDoTask>> = dao.getAllByHighPriority()

    override fun getTask(id: Int): Flow<ToDoTask> {
        return dao.get(taskId = id)
    }

    override suspend fun addTask(task: ToDoTask) {
        dao.add(task = task)
    }

    override suspend fun updateTask(task: ToDoTask) {
        dao.update(task = task)
    }

    override suspend fun deleteTask(task: ToDoTask) {
        dao.delete(task = task)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun search(query: String): Flow<List<ToDoTask>> {
        return dao.search(query = query)
    }
}