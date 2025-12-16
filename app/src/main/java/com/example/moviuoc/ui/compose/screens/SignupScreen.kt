package com.example.moviuoc.ui.compose.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moviuoc.core.isValidDuocEmail
import com.example.moviuoc.core.isValidPassword

/**
 * @param onSignup Se llama SOLO si todo está validado.
 *                 nombre, email, password, esConductor
 * @param onNavigateBack Vuelve al Login cuando el usuario presiona "Volver al inicio de sesión".
 */
@Composable
fun SignupScreen(
    onSignup: (String, String, String, Boolean) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    // Tipo de cuenta
    var esConductor by remember { mutableStateOf(false) }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var repeatPasswordError by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    fun validateInputs(): Boolean {
        var ok = true

        nombreError = when {
            nombre.isBlank() -> {
                ok = false; "El nombre es obligatorio"
            }
            else -> null
        }

        emailError = when {
            email.isBlank() -> {
                ok = false; "El correo es obligatorio"
            }
            !isValidDuocEmail(email) -> {
                ok = false; "Debe ser un correo @duocuc.cl válido"
            }
            else -> null
        }

        passwordError = when {
            password.isBlank() -> {
                ok = false; "La contraseña es obligatoria"
            }
            !isValidPassword(password) -> {
                ok = false; "Mínimo 8 caracteres, mayúscula, minúscula y número"
            }
            else -> null
        }

        repeatPasswordError = when {
            repeatPassword.isBlank() -> {
                ok = false; "Repite la contraseña"
            }
            repeatPassword != password -> {
                ok = false; "Las contraseñas no coinciden"
            }
            else -> null
        }

        return ok
    }

    fun handleSignupClick() {
        if (!validateInputs()) return
        isLoading = true
        onSignup(nombre.trim(), email.trim(), password, esConductor)
        isLoading = false
        Toast.makeText(context, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta MoviUOC",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                nombreError = null
            },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = nombreError != null
        )
        if (nombreError != null) {
            Text(
                text = nombreError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = { Text("Correo Duoc (@duocuc.cl)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null
        )
        if (emailError != null) {
            Text(
                text = emailError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null
        )
        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = {
                repeatPassword = it
                repeatPasswordError = null
            },
            label = { Text("Repetir contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = repeatPasswordError != null
        )
        if (repeatPasswordError != null) {
            Text(
                text = repeatPasswordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selección de tipo de cuenta
        Text(
            text = "Tipo de cuenta",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !esConductor,
                    onClick = { esConductor = false }
                )
                Text("Pasajero")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = esConductor,
                    onClick = { esConductor = true }
                )
                Text("Conductor")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { if (!isLoading) handleSignupClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.height(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Registrarse")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateBack) {
            Text("Volver al inicio de sesión")
        }
    }
}
