package com.example.moviuoc.data.remote

import com.example.moviuoc.data.local.ViajeEntity

// ---------- USUARIOS ----------
data class UsuarioDto(
    val id: Long? = null,
    val email: String,
    val nombre: String,
    val password: String,
    val tipo: String   // por ejemplo: "ALUMNO", "CONDUCTOR", etc.
)

// ---------- CONDUCTORES ----------
data class ConductorDto(
    val id: Long? = null,
    val licencia: String,
    val patente: String,
    val usuarioId: Long,   // FK al usuario
    val email: String,
    val nombre: String,
    val telefono: String
)

// ---------- VIAJES ----------
data class ViajeDto(
    val id: Long? = null,
    val conductorId: Long? = null,  // lo dejaremos opcional para no romper el backend
    val fechaMillis: Long,
    val cupos: Int,
    val origen: String,
    val destino: String,
    val precio: Int
)

// Mapeos entre Room y la API
fun ViajeEntity.toDto(conductorId: Long? = null): ViajeDto =
    ViajeDto(
        id = null, // el ID lo genera el backend
        conductorId = conductorId,
        fechaMillis = fechaMillis,
        cupos = cupos,
        origen = origen,
        destino = destino,
        precio = precio
    )

fun ViajeDto.toEntity(): ViajeEntity =
    ViajeEntity(
        id = (id ?: 0L).toInt(), // si viene sin id, Room generará uno
        fechaMillis = fechaMillis,
        cupos = cupos,
        origen = origen,
        destino = destino,
        precio = precio
    )

// ---------- API externa simple (ej: clima) ----------
data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current_weather: CurrentWeather?
)

data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val weathercode: Int
)
