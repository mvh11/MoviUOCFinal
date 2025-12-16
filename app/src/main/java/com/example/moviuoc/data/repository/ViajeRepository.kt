package com.example.moviuoc.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.moviuoc.data.local.AppDatabase
import com.example.moviuoc.data.local.ViajeDao
import com.example.moviuoc.data.local.ViajeEntity
import com.example.moviuoc.data.remote.ApiClient
import com.example.moviuoc.data.remote.toDto
import com.example.moviuoc.data.remote.toEntity

class ViajeRepository private constructor(
    private val dao: ViajeDao
) {

    private val api = ApiClient.viajesApi

    // Observa todos los viajes locales (Room) para usarlos en la UI
    fun observarTodos(): LiveData<List<ViajeEntity>> = dao.observarTodos()

    // Crea un viaje: primero en la API y luego lo guarda localmente
    suspend fun crearViaje(viaje: ViajeEntity) {
        // 1) Enviar a la API
        val creadoRemoto = api.createViaje(viaje.toDto())

        // 2) Guardar/actualizar en Room usando el id que venga del backend (si viene)
        val entityConId = if (creadoRemoto.id != null) {
            viaje.copy(id = creadoRemoto.id.toInt())
        } else {
            viaje
        }

        dao.insert(entityConId)
    }

    // Sincroniza completamente: borra lo local y trae todo desde la API
    suspend fun sincronizarDesdeApi() {
        val remotos = api.getViajes()
        val entities = remotos.map { it.toEntity() }

        dao.deleteAll()
        dao.insertAll(entities)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViajeRepository? = null

        fun get(context: Context): ViajeRepository =
            INSTANCE ?: synchronized(this) {
                val db = AppDatabase.get(context)
                ViajeRepository(db.viajeDao()).also { INSTANCE = it }
            }
    }
}
