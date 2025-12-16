package com.example.moviuoc.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviuoc.data.local.ViajeEntity
import com.example.moviuoc.ui.viaje.ViajeViewModel

@Composable
fun SearchTripScreen(
    viewModel: ViajeViewModel,
    onBack: () -> Unit
) {
    val viajes by viewModel.viajes.observeAsState(emptyList())
    var syncError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Viajes disponibles", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(10.dp))

        if (syncError != null) {
            Text(syncError!!, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(4.dp))
        }

        Button(
            onClick = {
                syncError = null
                try {
                    viewModel.sincronizar()
                } catch (e: Exception) {
                    syncError = "Error al sincronizar: ${e.message}"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar desde API")
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(viajes) { viaje ->
                ViajeCard(viaje)
            }
        }

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun ViajeCard(v: ViajeEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${v.origen} → ${v.destino}",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Cupos: ${v.cupos}")
            Text("Precio: ${v.precio} CLP")
        }
    }
}
