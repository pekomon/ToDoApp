package com.example.pekomon.todoapp.util

sealed class Result<out T> {
    object Idle : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T): Result<T>()
    data class Error(val error: Throwable): Result<Nothing>()
}
