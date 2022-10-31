package com.smartestidea.houseofthedargonquiz.data

import android.util.Log
import com.smartestidea.houseofthedargonquiz.R
import com.smartestidea.houseofthedargonquiz.data.model.Quiz

object BackgroundProvider {
    private val images = listOf(
        R.drawable.w,
        R.drawable.w1,
        R.drawable.w2,
        R.drawable.w3,
        R.drawable.w4,
        R.drawable.w5,
        R.drawable.w7,
        )
    fun getRandomsImage(): Int = images.randomOrNull()?: images[0]

}