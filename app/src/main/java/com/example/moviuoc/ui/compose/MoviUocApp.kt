package com.example.moviuoc.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviuoc.core.AuthStore
import com.example.moviuoc.core.SessionManager
import com.example.moviuoc.core.User
import com.example.moviuoc.data.remote.ConductorDto
import com.example.moviuoc.data.remote.UsuarioDto
import com.example.moviuoc.data.repository.ConductorRepository
import com.example.moviuoc.data.repository.UsuarioRepository
import com.example.moviuoc.ui.compose.screens.CreateTripScreen
import com.example.moviuoc.ui.compose.screens.HomeScreen
import com.example.moviuoc.ui.compose.screens.LoginScreen
import com.example.moviuoc.ui.compose.screens.ProfileScreen
import com.example.moviuoc.ui.compose.screens.SearchTripScreen
import com.example.moviuoc.ui.compose.screens.SignupScreen
import com.example.moviuoc.ui.compose.screens.SplashScreen
import com.example.moviuoc.ui.viaje.ViajeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MoviUocApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val session: SessionManager = remember {
        SessionManager(context)
    }

    val scope = rememberCoroutineScope()

    // ViewModel de viajes (Room + viajes-service)
    val viajeViewModel: ViajeViewModel = viewModel()

    val usuarioRepository = remember { UsuarioRepository() }
    val conductorRepository = remember { ConductorRepository() }

    // ---------- SPLASH ----------
    LaunchedEffect(Unit) {
        delay(1500)
        val destination = if (session.isLoggedIn()) "home" else "login"
        navController.navigate(destination) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            composable("splash") {
                SplashScreen()
            }

            // ---------- LOGIN ----------
            composable("login") {
                LoginScreen(
                    onLogin = { email, password ->
                        val ok = AuthStore.login(context, email, password)
                        if (!ok) {
                            Toast.makeText(
                                context,
                                "Correo o contraseña incorrecta",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@LoginScreen
                        }

                        val user = AuthStore.getUser(context, email)
                        val name = user?.name ?: email.substringBefore("@")
                        val esConductor = user?.career == "CONDUCTOR"

                        session.saveUserSession(
                            userId = session.getUserId() ?: 1L,
                            userName = name,
                            userEmail = email,
                            isDriver = esConductor
                        )

                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToSignup = {
                        navController.navigate("signup")
                    }
                )
            }

            // ---------- SIGNUP ----------
            composable("signup") {
                SignupScreen(
                    onSignup = { nombre, email, password, esConductor ->
                        // 1) Crear usuario local en AuthStore
                        val user = User(
                            email = email.trim(),
                            password = password,
                            name = nombre.trim(),
                            career = if (esConductor) "CONDUCTOR" else "PASAJERO"
                        )
                        AuthStore.createUser(context, user)

                        // 2) Registrar en microservicios
                        scope.launch {
                            try {
                                // usuarios-service
                                val usuarioCreado = usuarioRepository.crear(
                                    UsuarioDto(
                                        id = null,
                                        email = user.email,
                                        nombre = user.name,
                                        password = user.password,
                                        tipo = user.career
                                    )
                                )

                                // conductores-service sólo si es conductor
                                if (esConductor) {
                                    try {
                                        conductorRepository.crear(
                                            ConductorDto(
                                                id = null,
                                                licencia = "B",             // temporal
                                                patente = "SIN_PATENTE",    // temporal
                                                usuarioId = usuarioCreado.id ?: 0L,
                                                email = usuarioCreado.email,
                                                nombre = usuarioCreado.nombre,
                                                telefono = "SIN_TELEFONO"   // temporal
                                            )
                                        )
                                    } catch (_: Exception) {
                                        // No botamos la app si falla el microservicio de conductores
                                    }
                                }

                                // Guardar sesión remota OK
                                session.saveUserSession(
                                    userId = usuarioCreado.id ?: (session.getUserId() ?: 1L),
                                    userName = usuarioCreado.nombre,
                                    userEmail = usuarioCreado.email,
                                    isDriver = esConductor
                                )
                            } catch (_: Exception) {
                                // Si la API de usuarios falla, dejamos al menos la sesión local
                                session.saveUserSession(
                                    userId = session.getUserId() ?: 1L,
                                    userName = user.name,
                                    userEmail = user.email,
                                    isDriver = esConductor
                                )
                            }

                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ---------- HOME ----------
            composable("home") {
                HomeScreen(
                    session = session,
                    onCreateTrip = { navController.navigate("createTrip") },
                    onSearchTrip = { navController.navigate("searchTrip") },
                    onProfile = { navController.navigate("profile") },
                    onLogout = {
                        session.logout()
                        AuthStore.logout(context)
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }

            // ---------- CREAR VIAJE ----------
            composable("createTrip") {
                CreateTripScreen(
                    viewModel = viajeViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // ---------- BUSCAR VIAJE ----------
            composable("searchTrip") {
                SearchTripScreen(
                    viewModel = viajeViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // ---------- PERFIL ----------
            composable("profile") {
                ProfileScreen(
                    sessionManager = session,
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        session.logout()
                        AuthStore.logout(context)
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
