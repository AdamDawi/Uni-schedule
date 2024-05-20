package com.example.unischedule.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Session @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DATA = "Data"
        private const val LINK = "Link"
        val link = stringPreferencesKey(LINK)
    }

    fun getLink(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[link] ?: ""
        }
    }

    suspend fun setLink(newLink: String) {
        dataStore.edit { preference ->
            preference[link] = newLink
        }
    }
}