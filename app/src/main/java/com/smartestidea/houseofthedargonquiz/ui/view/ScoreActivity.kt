package com.smartestidea.houseofthedargonquiz.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.smartestidea.houseofthedargonquiz.R
import com.smartestidea.houseofthedargonquiz.data.HouseProvider
import com.smartestidea.houseofthedargonquiz.data.MusicProvider
import com.smartestidea.houseofthedargonquiz.data.model.Point
import com.smartestidea.houseofthedargonquiz.databinding.ActivityScoreBinding
import com.smartestidea.houseofthedargonquiz.ui.viewmodel.RankingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {
    private val binding:ActivityScoreBinding by lazy { ActivityScoreBinding.inflate(layoutInflater) }
    private val houses = mutableListOf<ImageView>()
    private var houseIndex =0
    private var puntuation =0
    private var handler = Handler()
    var runnable: Runnable? = null
    private var delay = 60
    private var count = 0
    private val rankingViewModel:RankingViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle = intent.extras
        puntuation = bundle!!.getInt("score")
        binding.animation.setAnimation(R.raw.confetti)
        val percent =getPercent(puntuation)
        MediaPlayer.create(this, R.raw.level_win).start()
        MediaPlayer.create(this, R.raw.crowd).start()
        MusicProvider.stopMedia()
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            if(++count<percent){
                binding.ivThrone.apply {
                    setPadding(0,convertInDp(count),0,0)
                    layoutParams.height =  convertInDp(100-count)
                }
            }else
                handler.removeCallbacksAndMessages(null)
        }.also { runnable = it }, delay.toLong())
        createHouses()
        houses.map { house->
            house.setOnClickListener { _->
                houseIndex = houses.indexOf(house)
                binding.tvHouse.text = (if(binding.etName.text.isNullOrEmpty()) "" else "${binding.etName.text} ")+HouseProvider.houses[houseIndex].name
                houses.map {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                houses[houseIndex].setBackgroundResource(R.drawable.back_alpha)
            }
        }
        binding.etName.addTextChangedListener {
            binding.tvHouse.text = (if(it.isNullOrEmpty()) "" else "$it ")+HouseProvider.houses[houseIndex].name
        }
        binding.btnRepeat.setOnClickListener {
            binding.btnRepeat.isEnabled = false
            binding.btnShare.isEnabled = false
            binding.btnShare.isVisible = false
            pushPoint()
            MusicProvider.setMedia(MediaPlayer.create(this,R.raw.intro_hoftd))
            MusicProvider.startMedia()
            Intent(this,QuizActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
        binding.btnShare.setOnClickListener {
            binding.btnRepeat.isEnabled = false
            binding.btnRepeat.isVisible = false
            binding.btnShare.isEnabled = false
            pushPoint()
            shareApp()
            finish()
        }
        binding.btnReturnHome.setOnClickListener {
            pushPoint()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun pushPoint() {
        rankingViewModel.pushPoint(Point(binding.tvHouse.text.toString(),puntuation,HouseProvider.houses[houseIndex].emblem))
        Toast.makeText(this,getString(R.string.already_saved),Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun createHouses(){
        repeat(7) {
            val param = LinearLayout.LayoutParams(
                convertInDp(50),
                convertInDp(60),
                1f,
            )
            param.setMargins(5,0,5,0)
            param.gravity = Gravity.CENTER
            val padding = convertInDp(5)
            houses.add(
                ImageView(this).apply {
                    setImageResource(HouseProvider.houses[it].emblem)
                    layoutParams = param
                    setPadding(padding,padding,padding,padding)
                }
            )
            binding.linear.addView(houses.last())
        }
        houses[0].setBackgroundResource(R.drawable.back_alpha)
    }
    private fun convertInDp(x:Int):Int{
        val scale = resources.displayMetrics.density
        return (x * scale + 0.5f).toInt()
    }
    private fun getPercent(x:Int):Float = x/3f
    private fun shareApp(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${binding.etName.text} " +
                    "${getString(R.string.of_the_house)} " +
                    "${HouseProvider.houses[houseIndex].name} " +
                    "${getString(R.string.has_obtained)} " +
                    "$puntuation ${getString(R.string.face_him)}\nhttps://www.apklis.cu/application/com.smartestidea.houseofthedargonquiz")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}