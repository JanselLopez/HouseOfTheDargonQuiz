package com.smartestidea.houseofthedargonquiz.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smartestidea.houseofthedargonquiz.data.model.Point

@Entity(tableName = "points_table")
data class PointsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val player:String,
    val points:Int,
    val emblem:Int
)

fun Point.toDomain()= PointsEntity(player = player,points= points,emblem = emblem)


