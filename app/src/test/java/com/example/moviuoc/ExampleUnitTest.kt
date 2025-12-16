package com.example.moviuoc

import com.example.moviuoc.core.isValidPassword
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Tests simples para las reglas de contraseña.
 */
class ValidationUtilsTest {

    @Test
    fun `password valida cumple todas las reglas`() {
        val password = "Abcdef1g"  // 8 caracteres, mayúscula, minúscula, número
        assertTrue(isValidPassword(password))
    }

    @Test
    fun `password muy corta es invalida`() {
        val password = "Abc1"
        assertFalse(isValidPassword(password))
    }

    @Test
    fun `password sin mayuscula es invalida`() {
        val password = "abcdef12"
        assertFalse(isValidPassword(password))
    }

    @Test
    fun `password sin numero es invalida`() {
        val password = "Abcdefgh"
        assertFalse(isValidPassword(password))
    }
}
