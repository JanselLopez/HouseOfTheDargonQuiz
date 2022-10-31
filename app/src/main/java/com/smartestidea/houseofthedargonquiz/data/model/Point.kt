package com.smartestidea.houseofthedargonquiz.data.model

import com.smartestidea.houseofthedargonquiz.data.database.entities.PointsEntity

data class Point(
    val player:String,
    val points:Int,
    val emblem:Int
)

fun PointsEntity.toDomain()= Point(player = player,points= points,emblem = emblem)
