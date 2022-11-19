package com.example.sem08.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.sem08.data.LugarDatabase
import com.example.sem08.model.Lugar
import com.example.sem08.repository.LugarRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LugarRepository
    val obtenerLugares: LiveData<List<Lugar>>

    init {
        val lugarDao = LugarDatabase.getDatabase(application).lugarDao()
        repository = LugarRepository(lugarDao)

        obtenerLugares = repository.obtenerLugares
    }

    fun guardarLugar(lugar: Lugar){
        viewModelScope.launch { repository.guardarLufar(lugar) }
    }
    fun eliminarLugar(lugar: Lugar){
        viewModelScope.launch { repository.eliminarLugar(lugar)}
    }
}