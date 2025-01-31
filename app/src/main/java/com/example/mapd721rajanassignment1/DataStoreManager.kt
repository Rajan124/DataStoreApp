package com.example.mapd721rajanassignment1
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

data class UserData(val id: String, val username: String, val courseName: String)

class DataStoreManager(private val context: Context) {
    companion object {
        private val ID_KEY = stringPreferencesKey("id_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val COURSE_KEY = stringPreferencesKey("course_key")
    }

    suspend fun saveData(id: String, username: String, courseName: String) {
        context.dataStore.edit { prefs ->
            prefs[ID_KEY] = id
            prefs[USERNAME_KEY] = username
            prefs[COURSE_KEY] = courseName
        }
    }

    suspend fun loadData(): UserData {
        val prefs = context.dataStore.data.first()
        return UserData(
            id = prefs[ID_KEY] ?: "",
            username = prefs[USERNAME_KEY] ?: "",
            courseName = prefs[COURSE_KEY] ?: ""
        )
    }

    suspend fun clearData() {
        context.dataStore.edit { it.clear() }
    }
}