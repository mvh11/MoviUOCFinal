package com.example.moviuoc.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joins")
data class JoinEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tripId: Int,
    val userEmail: String
)
