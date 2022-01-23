package com.example.pekomon.todoapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.data.repository.ToDoRepository
import com.example.pekomon.todoapp.util.SearchAppBarState
import com.example.pekomon.todoapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    // TODO: Refactor not exposing these

    private val _searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _allTasks = MutableStateFlow<Result<List<ToDoTask>>>(Result.Idle)
    val allTasks: StateFlow<Result<List<ToDoTask>>> = _allTasks

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun updateAllTAsks() {
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
}