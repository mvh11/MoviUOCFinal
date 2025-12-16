package com.example.moviuoc

import com.example.moviuoc.core.SessionManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * Test muy simple que demuestra uso de MockK.
 */
class SessionManagerTest {

    @Test
    fun `logout es llamado correctamente`() {
        // Creamos un mock relajado de SessionManager
        val sessionManager = mockk<SessionManager>(relaxed = true)

        // Llamamos al método que queremos verificar
        sessionManager.logout()

        // Verificamos que efectivamente se llamó una vez
        verify(exactly = 1) { sessionManager.logout() }
    }
}
