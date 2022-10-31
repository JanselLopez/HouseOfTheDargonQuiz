package com.smartestidea.houseofthedargonquiz.ui.view

import android.content.Intent
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.smartestidea.houseofthedargonquiz.R
import com.smartestidea.houseofthedargonquiz.data.BackgroundProvider
import com.smartestidea.houseofthedargonquiz.data.MusicProvider
import com.smartestidea.houseofthedargonquiz.databinding.ActivityQuizBinding
import com.smartestidea.houseofthedargonquiz.ui.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {
    private val binding:ActivityQuizBinding by lazy { ActivityQuizBinding.inflate(layoutInflater)}
    private val steps = mutableListOf<ImageView>()
    private val quizViewModel:QuizViewModel by viewModels()
    private var handler = Handler()
    var runnable: Runnable? = null
    private var delay = 1000
    var questionNumber = 1
    private val chips:List<Chip> by lazy { listOf(binding.chip1,binding.chip2,binding.chip3,binding.chip4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createSteps()
        quizViewModel.onCreate()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        binding.constraint.setBackgroundResource(BackgroundProvider.getRandomsImage())

        MusicProvider.setMedia(MediaPlayer.create(this, R.raw.intro_hoftd))
        MusicProvider.startMedia()

        quizViewModel.time.observe(this,{ time->
            binding.progressCircular.progress = time
            if(time==0){
                timeOver()
            }else {
                val color = when {
                    time > 10 -> R.color.black
                    time > 5 -> R.color.yellow
                    else -> R.color.purple_500
                }
                binding.progressCircular.progressDrawable!!.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        color
                    ), PorterDuff.Mode.SRC_IN
                )
                binding.tvTimeLimit.setTextColor(ContextCompat.getColor(this, color))
                binding.tvTimeLimit.text = time.toString()
            }
        })
        quizViewModel.question.observe(this, {quiz->
            binding.chip1.text = quiz.options[0]
            binding.chip2.text = quiz.options[1]
            binding.chip3.text = quiz.options[2]
            binding.chip4.text = quiz.options[3]
            binding.tvQuestion.text = resources.getString(quiz.question)
            chips.forEach {chip->
                chip.setOnClickListener {
                    handler.removeCallbacksAndMessages(null)
                    checkAnswer(chips.indexOf(chip),quiz.correct)
                }
            }
        })
        quizViewModel.point.observe(this,{
            binding.tvPoints.text = it.toString()
        })
        binding.btnLinear.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            MusicProvider.stopMedia()
        }
    }

    private fun timeOver(){
        handler.removeCallbacksAndMessages(null)
        chips.map {it.setChipBackgroundColorResource(R.color.purple_500)}
        chips[quizViewModel.question.value!!.correct].setChipBackgroundColorResource(R.color.teal)
        MediaPlayer.create(this, R.raw.game_error).start()
        nextQuestion(false)
    }

    private fun checkAnswer(checked: Int, correct: Int) {
        var isCorrect = true
        if(checked!=correct){
            chips[checked].setChipBackgroundColorResource(R.color.purple_500)
            MediaPlayer.create(this, R.raw.game_error).start()
            isCorrect = false
        }else{
            quizViewModel.time.value?.let { quizViewModel.addPoints(it) }
            MediaPlayer.create(this, R.raw.short_succes).start()
        }
            chips[correct].setChipBackgroundColorResource(R.color.teal)
            chips[correct].isChecked = true
        nextQuestion(isCorrect)
    }

    private fun nextQuestion(isCorrect:Boolean){
        chips.map { it.isEnabled = false }
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                ++questionNumber<=10 -> {
                    steps[questionNumber-1].setColorFilter(ContextCompat.getColor(this, if(isCorrect)R.color.teal else R.color.purple_500), PorterDuff.Mode.MULTIPLY)
                    steps[questionNumber-1].alpha = 1f
                    binding.tvQuestionNumber.text = if(questionNumber==10) "$questionNumber" else "0$questionNumber"
                    quizViewModel.nextQuestion()
                    quizViewModel.resetTime()
                    chips.map {
                        it.setChipBackgroundColorResource(R.color.cardview_light_background)
                        it.isEnabled = true
                    }
                    binding.chipGroup.clearCheck()
                    runTime()
                    binding.constraint.setBackgroundResource(BackgroundProvider.getRandomsImage())
                }
                else -> {
                     Intent(this,ScoreActivity::class.java).apply {
                         putExtra("score",quizViewModel.point.value)
                         startActivity(this)
                         finish()
                     }
                }
            }
        }, 1500)
    }
    private fun createSteps(){
        repeat(10) {
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f,
            )
            steps.add(
                ImageView(this).apply {
                    setImageResource(R.drawable.ic_baseline_horizontal_rule_24)
                    alpha = 0.5f
                    layoutParams = param
                }
            )
            binding.linear.addView(steps.last())
        }
        steps[0].setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.MULTIPLY)
        steps[0].alpha = 1f
    }

    override fun onResume() {
        runTime()
        super.onResume()
    }

    private fun runTime(){
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            quizViewModel.decreaseTime()
        }.also { runnable = it }, delay.toLong())
    }

}