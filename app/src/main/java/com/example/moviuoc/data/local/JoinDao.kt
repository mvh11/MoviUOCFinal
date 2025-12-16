package com.example.moviuoc.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JoinDao {

    @Insert
    suspend fun insert(join: JoinEntity)

    @Query("SELECT COUNT(*) FROM joins WHERE tripId = :tripId")
    fun countByTrip(tripId: Int): LiveData<Int>

    @Query("SELECT EXISTS(SELECT 1 FROM joins WHERE tripId=:tripId AND userEmail=:email)")
    fun isJoined(tripId: Int, email: String): LiveData<Boolean>
}
