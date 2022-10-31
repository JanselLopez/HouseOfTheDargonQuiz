package com.smartestidea.houseofthedargonquiz.data.model

data class Quiz(
    val id:Int,
    val question:Int,
    val options:MutableList<String>,
    val correct:Int
)