package com.smartestidea.houseofthedargonquiz.data.database.dao

import androidx.room.*
import com.smartestidea.houseofthedargonquiz.data.database.entities.PointsEntity

@Dao
interface RankDao {
    @Query("SELECT * FROM points_table ORDER BY points DESC")
    suspend fun getPoints():List<PointsEntity>?

    @Query("SELECT * FROM points_table where id = (Select id from points_table order by points desc limit 1)")
    suspend fun getMaxPoints():PointsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushPoint(point: PointsEntity)
}