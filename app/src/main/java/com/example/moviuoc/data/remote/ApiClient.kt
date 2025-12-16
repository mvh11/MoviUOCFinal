package com.example.moviuoc.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // IMPORTANTE:
    //  - usuarios-service:     http://localhost:8080/usuarios
    //  - viajes-service:       http://localhost:8082/viajes
    //  - conductores-service:  http://localhost:8081/conductores
    //
    // Desde el emulador de Android usamos 10.0.2.2 para apuntar a localhost del PC.

    private const val BASE_URL_USUARIOS = "http://10.0.2.2:8080/"
    private const val BASE_URL_CONDUCTORES = "http://10.0.2.2:8081/"
    private const val BASE_URL_VIAJES = "http://10.0.2.2:8082/"

    // API externa de ejemplo (Open-Meteo)
    private const val BASE_URL_WEATHER = "https://api.open-meteo.com/"

    // Logging de HTTP para depuración
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Retrofit para cada microservicio
    private val retrofitUsuarios: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_USUARIOS)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitConductores: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_CONDUCTORES)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitViajes: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_VIAJES)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitWeather: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_WEATHER)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // APIs de tus microservicios
    val usuariosApi: UsuarioApi by lazy { retrofitUsuarios.create(UsuarioApi::class.java) }
    val conductoresApi: ConductorApi by lazy { retrofitConductores.create(ConductorApi::class.java) }
    val viajesApi: ViajeApi by lazy { retrofitViajes.create(ViajeApi::class.java) }

    // API externa
    val weatherApi: WeatherApi by lazy { retrofitWeather.create(WeatherApi::class.java) }
}
