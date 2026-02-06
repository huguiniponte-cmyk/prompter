package com.prompter.app.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "security_prefs")

/**
 * Manager for app lock security (PIN and biometric)
 */
class SecurityManager(private val context: Context) {
    
    companion object {
        private val APP_LOCK_ENABLED = booleanPreferencesKey("app_lock_enabled")
        private val PIN_HASH = stringPreferencesKey("pin_hash")
        private val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
    }
    
    val isAppLockEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[APP_LOCK_ENABLED] ?: false
        }
    
    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[BIOMETRIC_ENABLED] ?: false
        }
    
    suspend fun setAppLockEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[APP_LOCK_ENABLED] = enabled
        }
    }
    
    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = enabled
        }
    }
    
    suspend fun setPin(pin: String) {
        val hash = hashPin(pin)
        context.dataStore.edit { preferences ->
            preferences[PIN_HASH] = hash
            preferences[APP_LOCK_ENABLED] = true
        }
    }
    
    suspend fun verifyPin(pin: String): Boolean {
        val storedHash = context.dataStore.data.map { preferences ->
            preferences[PIN_HASH]
        }
        
        var result = false
        storedHash.collect { hash ->
            result = hash == hashPin(pin)
        }
        return result
    }
    
    suspend fun hasPin(): Boolean {
        var result = false
        context.dataStore.data.map { preferences ->
            preferences[PIN_HASH] != null
        }.collect { hasPin ->
            result = hasPin
        }
        return result
    }
    
    private fun hashPin(pin: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(pin.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
