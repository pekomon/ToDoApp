package com.example.pekomon.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

private const val DB_VERSION = 1

@Database(
    entities = [ToDoDao::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class TodoDataBase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao
}