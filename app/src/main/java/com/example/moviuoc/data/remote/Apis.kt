package com.example.moviuoc.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// ---------- VIAJES ----------
interface ViajeApi {

    // http://10.0.2.2:8082/viajes
    @GET("viajes")
    suspend fun getViajes(): List<ViajeDto>

    @GET("viajes/{id}")
    suspend fun getViaje(@Path("id") id: Long): ViajeDto

    @POST("viajes")
    suspend fun createViaje(@Body dto: ViajeDto): ViajeDto

    @PUT("viajes/{id}")
    suspend fun updateViaje(
        @Path("id") id: Long,
        @Body dto: ViajeDto
    ): ViajeDto

    @DELETE("viajes/{id}")
    suspend fun deleteViaje(@Path("id") id: Long)
}

// ---------- USUARIOS ----------
interface UsuarioApi {

    // http://10.0.2.2:8080/usuarios
    @GET("usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>

    @GET("usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Long): UsuarioDto

    @POST("usuarios")
    suspend fun createUsuario(@Body dto: UsuarioDto): UsuarioDto

    @PUT("usuarios/{id}")
    suspend fun updateUsuario(
        @Path("id") id: Long,
        @Body dto: UsuarioDto
    ): UsuarioDto

    @DELETE("usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Long)

    // Ejemplo de login básico (ajusta si tu backend usa otra ruta o método)
    @GET("usuarios/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): UsuarioDto
}

// ---------- CONDUCTORES ----------
interface ConductorApi {

    // http://10.0.2.2:8081/conductores
    @GET("conductores")
    suspend fun getConductores(): List<ConductorDto>

    @GET("conductores/{id}")
    suspend fun getConductor(@Path("id") id: Long): ConductorDto

    @POST("conductores")
    suspend fun createConductor(@Body dto: ConductorDto): ConductorDto

    @PUT("conductores/{id}")
    suspend fun updateConductor(
        @Path("id") id: Long,
        @Body dto: ConductorDto
    ): ConductorDto

    @DELETE("conductores/{id}")
    suspend fun deleteConductor(@Path("id") id: Long)
}

/**
 * API externa (clima)
 */
interface WeatherApi {

    // /v1/forecast?latitude=-33.45&longitude=-70.66&current_weather=true
    @GET("/v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true
    ): WeatherResponse
}
