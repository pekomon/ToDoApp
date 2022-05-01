package com.example.pekomon.todoapp.util

object Consts {
    const val TODO_TABLE_NAME = "todo"
    const val DATABASE_NAME = "todo_db"

    const val SPLASH_SCREEN = "splash"
    const val LIST_SCREEN = "list/{action}"
    const val TASK_SCREEN = "task/{taskId}"

    const val LIST_ARGUMENT_KEY = "action"
    const val TASK_ARGUMENT_KEY = "taskId"

    const val PREFERENCES_NAME = "todo_prefs"
    const val PREFERENCE_KEY_SORT_STATE = "sort_state"

    const val TASK_ID_ADD_NEW = -1

    const val TITLE_MAX_LENGTH = 25

    const val LIST_ITEM_ANIMATION_DURATION_MILLIS = 300
    const val SPLASH_SCREEN_DELAY_MILLIS = 2000L
}