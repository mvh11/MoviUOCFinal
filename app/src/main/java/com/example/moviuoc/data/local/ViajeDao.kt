package com.example.moviuoc.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ViajeDao {

    @Insert
    suspend fun insert(viaje: ViajeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(viajes: List<ViajeEntity>)

    @Query("DELETE FROM viajes")
    suspend fun deleteAll()

    @Query("SELECT * FROM viajes ORDER BY fechaMillis DESC")
    fun observarTodos(): LiveData<List<ViajeEntity>>
}
