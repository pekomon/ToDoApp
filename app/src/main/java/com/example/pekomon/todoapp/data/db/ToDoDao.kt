package com.example.pekomon.todoapp.data.db

import androidx.room.*
import com.example.pekomon.todoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun add(task: ToDoTask)

    @Update
    suspend fun update(task: ToDoTask)

    @Delete
    suspend fun delete(task: ToDoTask)

    @Query("SELECT * FROM todo order by id ASC")
    fun getAll(): Flow<List<ToDoTask>>

    @Query("select * from todo where id = :taskId")
    fun get(taskId: Int): Flow<ToDoTask>

    @Query("DELETE FROM todo")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo where title LIKE :query OR description LIKE :query")
    fun search(query: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo ORDER BY CASE " +
            "WHEN priority = 'LOW' then 1 " +
            "WHEN priority = 'MEDIUM' then 2 " +
            "WHEN priority = 'HIGH' then 3 END"
    )
    fun getAllByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo ORDER BY CASE " +
            "WHEN priority = 'HIGH' then 1 " +
            "WHEN priority = 'MEDIUM' then 2 " +
            "WHEN priority = 'LOW' then 3 END"
    )
    fun getAllByHighPriority(): Flow<List<ToDoTask>>



}