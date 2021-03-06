package com.example.pekomon.todoapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.data.repository.DataStoreRepository
import com.example.pekomon.todoapp.data.repository.ToDoRepository
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts
import com.example.pekomon.todoapp.util.SearchAppBarState
import com.example.pekomon.todoapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    private val _searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _searchedTasks = MutableStateFlow<Result<List<ToDoTask>>>(Result.Idle)
    val searchedTasks: StateFlow<Result<List<ToDoTask>>> = _searchedTasks

    private val _allTasks = MutableStateFlow<Result<List<ToDoTask>>>(Result.Idle)
    val allTasks: StateFlow<Result<List<ToDoTask>>> = _allTasks

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    private val _sortState = MutableStateFlow<Result<Priority>>(Result.Idle)
    val sortState: StateFlow<Result<Priority>> = _sortState

    // converting to state flow
    val lowPriorityTasks: StateFlow<List<ToDoTask>> = repository.getAllByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val highPriorityTasks: StateFlow<List<ToDoTask>> = repository.getAllByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    init {
        getAllTasks()
        readSortState()
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.addTask(task = todoTask)
        }
        _searchAppBarState.value = SearchAppBarState.CLOSED
        _searchTextState.value = ""
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(task = task)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(task = task)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun handleDatabaseActions(action: Action) {
        Log.d("Viewmodel", "handleDatabaseActions $action")
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {}
        }
    }

    private fun getAllTasks() {
        _allTasks.value = Result.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = Result.Success(it)
                }
            }
        } catch (t: Throwable) {
            _allTasks.value = Result.Error(t)
        }
    }

    fun getTask(taskId: Int) {
        viewModelScope.launch {
            repository.getTask(taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    fun searchTasks(searchQuery: String) {
        _searchedTasks.value = Result.Loading
        try {
            viewModelScope.launch {
                repository.search(query = searchQuery)
                    .collect { searchedTasks ->
                        _searchedTasks.value = Result.Success(searchedTasks)
                    }
            }
        } catch (t: Throwable) {
            _searchedTasks.value = Result.Error(t)
        }
        _searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun openSearchAppBar() {
        _searchAppBarState.value = SearchAppBarState.OPEN
    }

    fun closeSearchAppBar() {
        _searchAppBarState.value = SearchAppBarState.CLOSED
    }

    fun searchTextChanged(
        newText: String = ""
    ) {
        _searchTextState.value = newText
    }

    fun updateUI(task: ToDoTask?) {
        if (task != null) {
            id.value = task.id
            title.value = task.title
            description.value = task.description
            priority.value = task.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < Consts.TITLE_MAX_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private fun readSortState() {
        _sortState.value = Result.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = Result.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = Result.Error(e)
        }
    }
}