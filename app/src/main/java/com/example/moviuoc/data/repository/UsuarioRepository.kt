package com.example.moviuoc.data.repository

import com.example.moviuoc.data.remote.ApiClient
import com.example.moviuoc.data.remote.UsuarioDto

class UsuarioRepository {

    private val api = ApiClient.usuariosApi

    suspend fun listar(): List<UsuarioDto> = api.getUsuarios()

    suspend fun crear(dto: UsuarioDto): UsuarioDto = api.createUsuario(dto)

    suspend fun actualizar(id: Long, dto: UsuarioDto): UsuarioDto =
        api.updateUsuario(id, dto)

    suspend fun eliminar(id: Long) {
        api.deleteUsuario(id)
    }
}
