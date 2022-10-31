package com.smartestidea.houseofthedargonquiz.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartestidea.houseofthedargonquiz.data.database.dao.RankDao
import com.smartestidea.houseofthedargonquiz.data.database.entities.PointsEntity

@Database(entities = [PointsEntity::class],version = 1)
abstract class RankDatabase:RoomDatabase() {

    abstract fun getRankDao(): RankDao
}