package com.example.sem08.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sem08.model.Lugar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class LugarDao {
    //Firebase Vars
    private var codigoUsuario: String
    private var firestore: FirebaseFirestore

    init {
        codigoUsuario = Firebase.auth.currentUser?.email.toString()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

    }

    fun getLugares(): MutableLiveData<List<Lugar>>{
        val listaLugares = MutableLiveData<List<Lugar>>()

        firestore
            .collection("LugaresViernes")
            .document()
            .collection("misLugares")
            .addSnapshotListener { snapshot, e ->
                if (e!= null){
                    return@addSnapshotListener
                }
                if (snapshot != null){
                    val lista = ArrayList<Lugar>()
                    var lugares = snapshot.documents
                    lugares.forEach{
                        val lugar = it.toObject(Lugar::class.java)
                        if (lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }
            }
        return listaLugares

    }


    fun guardarLugar(lugar: Lugar) {
        val document: DocumentReference
        if (lugar.id.isEmpty()) {
            //Agregar
            document = firestore.collection("LugaresViernes")
                .document()
                .collection("misLugares")
                .document()
            lugar.id = document.id
        } else {
            //Actualizar
            document = firestore.collection("LugaresViernes")
                .document()
                .collection("misLugares")
                .document(lugar.id)
        }
        document.set(lugar)
            .addOnCompleteListener {
                Log.d("guardarLugar", "Guardado con exito")
            }
            .addOnCanceledListener {
                Log.e("guardarLugar", "Error al guardar")
            }
    }


    fun eliminarLugar(lugar: Lugar) {
        if (lugar.id.isNotEmpty()) {
            firestore
                .collection("LugaresViernes")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
                .delete()
                .addOnCompleteListener {
                    Log.d("guardarLugar", "Actualizado con exito")
                }
                .addOnCanceledListener {
                    Log.e("guardarLugar", "Error al actualizar")
                }
        }
    }
}