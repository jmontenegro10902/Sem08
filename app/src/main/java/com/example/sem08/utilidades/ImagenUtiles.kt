package com.example.sem08.utilidades

import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.example.sem08.BuildConfig
import java.io.File
import java.nio.file.Path

class ImagenUtiles(
    private val contexto: Context,
    btPhoto: ImageButton,
    btRotaI: ImageButton,
    btRotaD: ImageButton,
    private val image: ImageView,
    private var tomarFotoActivity: ActivityResultLauncher<Intent>
    ){
    init {
        btPhoto.setOnClickListener { tomarfoto() }
    }

    lateinit var imagenFile: File
    private lateinit var currentPhotoPath: String

    private fun tomarfoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(contexto.packageManager) != null){
            imagenFile = crearImagenFile()
            val photoURI: FileProvider.getUriForFile(
            contexto,
            BuildConfig.APPLICATION_ID+".provider",
            imagenFile
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            tomarFotoActivity.launch(intent)
        }
    }

    private fun crearImagenFile(): File {
        val archivo = OtrosUtiles.getTempFile("image_")
        val storageDir: File?
    }
}