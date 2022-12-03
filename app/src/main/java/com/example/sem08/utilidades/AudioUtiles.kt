package com.example.sem08.utilidades

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sem08.R
import com.google.android.gms.dynamic.IFragmentWrapper
import java.io.File
import java.io.IOException


@RequiresApi(Build.VERSION_CODES.O)
class AudioUtiles(
    private val actividad: Activity,
    private val context: Context,
    private val btnAccion: ImageButton,
    private val btnPlay: ImageButton,
    private val btnDelete: ImageButton,
    private val msgInicioNotaAudio: String,
    private val msgDeleteNotaAudio: String,
) {
    init {
        btnAccion.setOnClickListener { grabaDetiene() }
        btnPlay.setOnClickListener { reproducirAudio() }
        btnDelete.setOnClickListener { eliminarAudio() }

        btnDelete.isEnabled = false
        btnPlay.isEnabled = false
    }

    private var mediaRecorder: MediaRecorder? = null
    private var grabando = false
    var audioFile: File = File.createTempFile("audio_",  ".mp3")

    private fun grabaDetiene(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            val permission = arrayOf(Manifest.permission.RECORD_AUDIO)
            ActivityCompat.requestPermissions(actividad, permission,0)
        }
        else{
            grabando = if (!grabando){
                RecorderInit()
                IniciarGrabar()
                true
            }
            else{
                detenerAudio()
                false
            }
        }
    }

    private fun RecorderInit(){
        if (audioFile.exists() && audioFile.isFile){
            audioFile.delete()
        }
        val archivo = OtrosUtiles.getTempFile("audio_")
        audioFile = File.createTempFile(archivo,".mp3")
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioChannels(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder!!.setOutputFile(audioFile)
    }
    private fun IniciarGrabar(){
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            Toast.makeText(context, msgInicioNotaAudio, Toast.LENGTH_LONG).show()
            btnDelete.isEnabled = false
            btnPlay.isEnabled = false
            btnAccion.setImageResource(R.drawable.ic_stop)

        }
        catch (e:java.lang.IllegalStateException){
            e.printStackTrace()
        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }
    private fun detenerAudio(){
        btnDelete.isEnabled = true
        btnPlay.isEnabled = true
        mediaRecorder?.stop()
        mediaRecorder?.release()
        Toast.makeText(context, msgDeleteNotaAudio, Toast.LENGTH_LONG).show()
        btnAccion.setImageResource(R.drawable.ic_mic)
    }


    private fun reproducirAudio(){
        try {
            if (audioFile.exists() && audioFile.canRead()){
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource(audioFile.path)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }

    private fun eliminarAudio(){
        try {
            if (audioFile.exists() && audioFile.canRead()){
                audioFile.delete()
                btnDelete.isEnabled = false
                btnPlay.isEnabled = false
            }
        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }
}