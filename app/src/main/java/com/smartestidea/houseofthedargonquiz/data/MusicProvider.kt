package com.smartestidea.houseofthedargonquiz.data

import android.media.MediaPlayer

object MusicProvider {
    private lateinit var mediaPlayer: MediaPlayer
    fun setMedia(mp: MediaPlayer){ mediaPlayer = mp }
    fun startMedia(){
        if (!mediaPlayer.isPlaying){
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }
    }
    fun stopMedia()= mediaPlayer.stop()
}