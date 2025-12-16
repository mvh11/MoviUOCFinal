package com.example.moviuoc.ui.compose.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.moviuoc.core.SessionManager
import java.io.File
import java.io.FileOutputStream

/**
 * Pantalla de perfil:
 * - Muestra datos básicos del usuario (nombre, email).
 * - Permite sacar una foto con la cámara y guardarla como avatar.
 * - Foto se guarda en almacenamiento interno como profile.jpg
 */
@Composable
fun ProfileScreen(
    sessionManager: SessionManager,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current

    // Cargamos la foto si ya existe
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        val file = File(context.filesDir, "profile.jpg")
        if (file.exists()) {
            val bmp = BitmapFactory.decodeFile(file.absolutePath)
            profileBitmap = bmp
        }
    }

    // Launcher para tomar foto con la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            profileBitmap = bitmap
            // Guardar en almacenamiento interno
            val file = File(context.filesDir, "profile.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            Toast.makeText(context, "Foto de perfil actualizada", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher para permisos de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(
                context,
                "Se necesita permiso de cámara para tomar la foto",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun openCamera() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            cameraLauncher.launch(null)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Datos del usuario desde SessionManager
    val userName = sessionManager.getUserName() ?: "Usuario MoviUOC"
    val userEmail = sessionManager.getUserEmail() ?: "correo@duocuc.cl"

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Mi perfil",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Avatar circular (click para cambiar foto)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clickable { openCamera() },
                contentAlignment = Alignment.Center
            ) {
                val bmp = profileBitmap
                if (bmp != null) {
                    Image(
                        bitmap = bmp.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "Tocar para\nagregar foto",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userName, style = MaterialTheme.typography.titleMedium)
            Text(text = userEmail, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { openCamera() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar nueva foto")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    sessionManager.logout()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
