package com.example.moviuoc.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.moviuoc.data.local.ViajeEntity
import com.example.moviuoc.ui.viaje.ViajeViewModel

@Composable
fun CreateTripScreen(
    viewModel: ViajeViewModel,
    onBack: () -> Unit
) {
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var cupos by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    var error by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Crear viaje", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = origen,
            onValueChange = { origen = it },
            label = { Text("Origen") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cupos,
            onValueChange = { cupos = it },
            label = { Text("Cupos disponibles") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Monto por pasajero (CLP)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (success) {
            Text(
                text = "Viaje guardado correctamente",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = {
                val cuposInt = cupos.toIntOrNull()
                val precioInt = precio.toIntOrNull()

                if (origen.isBlank() || destino.isBlank() ||
                    cuposInt == null || precioInt == null
                ) {
                    error = "Completa todos los datos con valores numéricos válidos"
                    success = false
                } else if (cuposInt <= 0 || precioInt <= 0) {
                    error = "Cupos y monto deben ser mayores a 0"
                    success = false
                } else {
                    error = null
                    val viaje = ViajeEntity(
                        fechaMillis = System.currentTimeMillis(),
                        cupos = cuposInt,
                        origen = origen,
                        destino = destino,
                        precio = precioInt
                    )
                    viewModel.crearViaje(viaje)
                    success = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar viaje")
        }

        Spacer(Modifier.height(8.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
