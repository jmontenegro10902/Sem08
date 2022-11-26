package com.example.sem08.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.sem08.data.LugarDao
import com.example.sem08.model.Lugar
import com.example.sem08.repository.LugarRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LugarRepository = LugarRepository(LugarDao())
    val obtenerLugares: MutableLiveData<List<Lugar>>

    init {
        obtenerLugares = repository.obtenerLugares
    }

    fun guardarLugar(lugar: Lugar){
        repository.guardarLufar(lugar)
    }
    fun eliminarLugar(lugar: Lugar){
        repository.eliminarLugar(lugar)
    }
}