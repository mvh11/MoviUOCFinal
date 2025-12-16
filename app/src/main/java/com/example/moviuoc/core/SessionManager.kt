package com.example.moviuoc.core

import android.content.Context

/**
 * Maneja la sesión del usuario usando SharedPreferences.
 * Guarda: id, nombre, email y si es conductor.
 */
class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserSession(
        userId: Long,
        userName: String,
        userEmail: String,
        isDriver: Boolean
    ) {
        prefs.edit()
            .putLong(KEY_USER_ID, userId)
            .putString(KEY_USER_NAME, userName)
            .putString(KEY_USER_EMAIL, userEmail)
            .putBoolean(KEY_IS_DRIVER, isDriver)
            .apply()
    }

    fun getUserId(): Long? {
        val id = prefs.getLong(KEY_USER_ID, -1L)
        return if (id == -1L) null else id
    }

    fun getUserName(): String? =
        prefs.getString(KEY_USER_NAME, null)

    fun getUserEmail(): String? =
        prefs.getString(KEY_USER_EMAIL, null)

    fun isDriver(): Boolean =
        prefs.getBoolean(KEY_IS_DRIVER, false)

    fun isLoggedIn(): Boolean =
        prefs.contains(KEY_USER_ID)

    fun logout() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "movi_uoc_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_DRIVER = "is_driver"
    }
}
