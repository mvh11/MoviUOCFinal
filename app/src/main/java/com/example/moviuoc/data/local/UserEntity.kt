package com.example.moviuoc.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UserEntity(
    @PrimaryKey val email: String,
    val nombre: String,
    val carrera: String
)
