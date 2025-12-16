package com.example.moviuoc.core

import android.content.Context

data class User(
    val email: String,
    val password: String,
    val name: String,
    val career: String
)

/**
 * Pequeña "base de datos" en SharedPreferences para registrar y autenticar usuarios locales.
 * Esto cumple con el CRUD de Usuario del lado app (además del microservicio remoto).
 */
object AuthStore {

    private const val PREFS = "auth_prefs"
    private const val LOGGED = "logged_in_email"

    private fun prefs(ctx: Context) =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private fun key(email: String) = "user_" + email.trim().lowercase()

    fun createUser(ctx: Context, user: User) {
        val json = "${user.email}|${user.password}|${user.name}|${user.career}"
        prefs(ctx).edit().putString(key(user.email), json).apply()
    }

    fun getUser(ctx: Context, email: String): User? {
        val raw = prefs(ctx).getString(key(email), null) ?: return null
        val parts = raw.split("|")
        if (parts.size < 4) return null
        return User(
            email = parts[0],
            password = parts[1],
            name = parts[2],
            career = parts[3]
        )
    }

    fun updateUser(ctx: Context, user: User) = createUser(ctx, user)

    fun deleteUser(ctx: Context, email: String) {
        prefs(ctx).edit().remove(key(email)).apply()
    }

    fun login(ctx: Context, email: String, password: String): Boolean {
        val u = getUser(ctx, email) ?: return false
        val ok = u.password == password
        if (ok) {
            prefs(ctx).edit().putString(LOGGED, u.email).apply()
        }
        return ok
    }

    fun isLogged(ctx: Context): Boolean =
        prefs(ctx).getString(LOGGED, null) != null

    fun logout(ctx: Context) {
        prefs(ctx).edit().remove(LOGGED).apply()
    }
}
