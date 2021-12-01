package com.example.pekomon.todoapp.di

import com.example.pekomon.todoapp.data.db.ToDoDao
import com.example.pekomon.todoapp.data.repository.ToDoRepository
import com.example.pekomon.todoapp.data.repository.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTodoRepository(
        dao: ToDoDao
    ) : ToDoRepository {
        return ToDoRepositoryImpl(dao)
    }
}