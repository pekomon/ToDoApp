package com.example.pekomon.todoapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.util.Consts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Consts.PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = Consts.PREFERENCE_KEY_SORT_STATE)
    }

    private val dataStore = context.dataStore

    override suspend fun persistSortState(priority: Priority) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.sortKey] = priority.name
        }
    }

    override val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val state = prefs[PreferenceKeys.sortKey] ?: Priority.NONE.name
            state
        }
}
