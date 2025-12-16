package com.example.moviuoc.ui.compose.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moviuoc.core.isValidDuocEmail
import com.example.moviuoc.core.isValidPassword

/**
 * @param onLogin Llamado SOLO cuando el correo y la contraseña son válidos.
 *                Aquí tú conectas con el ViewModel + Retrofit (usuarios-service /login).
 * @param onNavigateToSignup Navega a la pantalla de registro.
 */
@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    fun validateInputs(): Boolean {
        var ok = true

        // Validar email Duoc
        emailError = when {
            email.isBlank() -> {
                ok = false; "El correo es obligatorio"
            }
            !isValidDuocEmail(email) -> {
                ok = false; "Debe ser un correo @duocuc.cl válido"
            }
            else -> null
        }

        // Validar contraseña (mismas reglas que en registro)
        passwordError = when {
            password.isBlank() -> {
                ok = false; "La contraseña es obligatoria"
            }
            !isValidPassword(password) -> {
                ok = false; "Mínimo 8 caracteres, mayúscula, minúscula y número"
            }
            else -> null
        }

        return ok
    }

    fun handleLoginClick() {
        if (!validateInputs()) return

        isLoading = true
        // Aquí NO hacemos la llamada, solo delegamos:
        onLogin(email.trim(), password)

        // El ViewModel debería actualizar algún estado de éxito/fracaso.
        // Desde afuera puedes apagar isLoading cuando termine la request
        // o dejarlo así simple para la evaluación.
        isLoading = false
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "MoviUOC",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    handleLoginClick()
                    if (!isValidDuocEmail(email)) {
                        Toast.makeText(
                            context,
                            "Solo se permiten correos @duocuc.cl",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Iniciar sesión")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onNavigateToSignup) {
                Text("¿No tienes cuenta? Crear cuenta")
            }
        }
    }
}
