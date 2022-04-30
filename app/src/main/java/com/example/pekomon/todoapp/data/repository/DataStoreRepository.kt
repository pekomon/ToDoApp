package com.example.pekomon.todoapp.data.repository

import com.example.pekomon.todoapp.data.models.Priority
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun persistSortState(priority: Priority)
    val readSortState: Flow<String>
}