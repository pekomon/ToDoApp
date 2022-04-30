package com.example.pekomon.todoapp.di

import android.content.Context
import com.example.pekomon.todoapp.data.db.ToDoDao
import com.example.pekomon.todoapp.data.repository.DataStoreRepository
import com.example.pekomon.todoapp.data.repository.DataStoreRepositoryImpl
import com.example.pekomon.todoapp.data.repository.ToDoRepository
import com.example.pekomon.todoapp.data.repository.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) : DataStoreRepository {
        return DataStoreRepositoryImpl(context = context)
    }
}