package com.example.moviuoc.data.repository

import com.example.moviuoc.data.remote.ApiClient
import com.example.moviuoc.data.remote.ConductorDto

class ConductorRepository {

    private val api = ApiClient.conductoresApi

    suspend fun listar(): List<ConductorDto> = api.getConductores()

    suspend fun crear(dto: ConductorDto): ConductorDto = api.createConductor(dto)

    suspend fun actualizar(id: Long, dto: ConductorDto): ConductorDto =
        api.updateConductor(id, dto)

    suspend fun eliminar(id: Long) {
        api.deleteConductor(id)
    }
}
