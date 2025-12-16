package com.example.moviuoc.ui.viaje

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviuoc.data.local.ViajeEntity
import com.example.moviuoc.data.repository.ViajeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViajeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ViajeRepository.get(app)

    val viajes: LiveData<List<ViajeEntity>> = repo.observarTodos()

    fun crearViaje(v: ViajeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.crearViaje(v)
        }
    }

    fun sincronizar() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.sincronizarDesdeApi()
        }
    }
}
