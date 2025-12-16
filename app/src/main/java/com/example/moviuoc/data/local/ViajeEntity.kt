package com.example.moviuoc.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viajes")
data class ViajeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fechaMillis: Long,
    val cupos: Int,
    val origen: String,
    val destino: String,
    val precio: Int
)
