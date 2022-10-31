package com.smartestidea.houseofthedargonquiz.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartestidea.houseofthedargonquiz.databinding.ActivityRankingBinding
import com.smartestidea.houseofthedargonquiz.ui.adapter.ScoreAdapter
import com.smartestidea.houseofthedargonquiz.ui.viewmodel.RankingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingActivity : AppCompatActivity() {
    private val binding:ActivityRankingBinding by lazy{ActivityRankingBinding.inflate(layoutInflater)}
    private val rankingViewModel:RankingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        binding.rvScores.layoutManager = LinearLayoutManager(this)

        rankingViewModel.onCreate()

        rankingViewModel.points.observe(this,{
            binding.rvScores.adapter = ScoreAdapter(it)
        })
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }
}