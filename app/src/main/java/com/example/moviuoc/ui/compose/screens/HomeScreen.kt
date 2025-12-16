package com.example.moviuoc.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviuoc.core.SessionManager

@Composable
fun HomeScreen(
    session: SessionManager,
    onCreateTrip: () -> Unit,
    onSearchTrip: () -> Unit,
    onProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val name = session.getUserName() ?: "Estudiante"
    val email = session.getUserEmail() ?: ""
    val esConductor = session.isDriver()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Hola, $name",
            style = MaterialTheme.typography.headlineMedium
        )

        if (email.isNotBlank()) {
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (esConductor) {
            Button(
                onClick = onCreateTrip,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear viaje")
            }
        } else {
            Text(
                text = "Solo los conductores pueden crear viajes. Puedes buscar viajes disponibles.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = onSearchTrip,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar viajes")
        }

        Button(
            onClick = onProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mi perfil")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
