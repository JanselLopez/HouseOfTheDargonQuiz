package com.smartestidea.houseofthedargonquiz.ui.view

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.smartestidea.houseofthedargonquiz.R
import com.smartestidea.houseofthedargonquiz.data.MusicProvider
import com.smartestidea.houseofthedargonquiz.databinding.ActivityMainBinding
import com.smartestidea.houseofthedargonquiz.ui.viewmodel.MusicViewModel
import com.smartestidea.houseofthedargonquiz.ui.viewmodel.RankingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


private val Context.dataStore by preferencesDataStore(name ="music")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val rankingViewModel:RankingViewModel by viewModels()
    private val musicViewModel:MusicViewModel by viewModels()
    private val playKey = booleanPreferencesKey("play")

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        screenSplash.setKeepOnScreenCondition{false}
        rankingViewModel.onCreate()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        rankingViewModel.onCreate()
        lifecycleScope.launch {
            readPlay()
        }

        binding.btnPlay.setOnClickListener{ showActivity(Intent(this, QuizActivity::class.java)) }

        binding.btnRanking.setOnClickListener{ showActivity(Intent(this, RankingActivity::class.java)) }

        binding.btnAbout.setOnClickListener { showActivity(Intent(this, AboutActivity::class.java)) }

        rankingViewModel.maxPoint.observe(this,{ binding.tvMax.text = it.points.toString() })

        MusicProvider.setMedia(MediaPlayer.create(this, R.raw.intro_hoftd))

        musicViewModel.playMusic.observe(this,{
            if(it){
                binding.btnMusic.setImageResource(R.drawable.ic_audio_speaker_on_svgrepo_com)
                MusicProvider.setMedia(MediaPlayer.create(this, R.raw.intro_hoftd))
                MusicProvider.startMedia()
            }else{
                binding.btnMusic.setImageResource(R.drawable.ic_audio_speaker_off_svgrepo_com_1_)
                MusicProvider.stopMedia()
            }
            lifecycleScope.launch {
                setPlay(it)
            }
        })
        binding.btnMusic.setOnClickListener {
           musicViewModel.playMusic.postValue(!(musicViewModel.playMusic.value)!!)
        }
    }

    private suspend fun readPlay() {
        dataStore.edit { preferences ->
            musicViewModel.playMusic.postValue(preferences[playKey]?:true)
            Log.e("play",preferences[playKey].toString())
        }
    }

    private fun showActivity(intent: Intent) {
        MediaPlayer.create(this, R.raw.draw_sword).start()
        startActivity(intent)
    }
    private suspend fun setPlay(play:Boolean) {
        dataStore.edit { preferences ->
            preferences[playKey] = play
            Log.e("play",preferences[playKey].toString())
        }
    }

}