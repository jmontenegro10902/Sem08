package com.example.sem08.repository

import androidx.lifecycle.LiveData
import com.example.sem08.data.LugarDao
import com.example.sem08.model.Lugar

class LugarRepository(private  val lugarDao: LugarDao) {
    suspend fun guardarLufar(lugar: Lugar){
        if (lugar.id== 0){
            lugarDao.agregarLugar(lugar)
        }
        else{
            lugarDao.actualizarLugar(lugar)
        }
    }

    suspend fun eliminarLugar(lugar: Lugar){
        lugarDao.eliminarLugar(lugar)
    }

    val obtenerLugares: LiveData<List<Lugar>> = lugarDao.getLugares()
}