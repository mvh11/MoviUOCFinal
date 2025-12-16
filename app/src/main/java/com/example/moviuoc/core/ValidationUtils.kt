package com.example.moviuoc.core

import android.util.Patterns

/**
 * Valida que el correo sea válido y termine en @duocuc.cl
 */
fun isValidDuocEmail(email: String): Boolean {
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return false
    return email.lowercase().endsWith("@duocuc.cl")
}

/**
 * Reglas de contraseña:
 * - mínimo 8 caracteres
 * - al menos 1 mayúscula
 * - al menos 1 minúscula
 * - al menos 1 número
 */
fun isValidPassword(password: String): Boolean {
    if (password.length < 8) return false
    if (!password.any { it.isUpperCase() }) return false
    if (!password.any { it.isLowerCase() }) return false
    if (!password.any { it.isDigit() }) return false
    return true
}
