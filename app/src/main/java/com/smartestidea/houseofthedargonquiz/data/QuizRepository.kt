package com.smartestidea.houseofthedargonquiz.data

import com.smartestidea.houseofthedargonquiz.data.database.dao.RankDao
import com.smartestidea.houseofthedargonquiz.data.database.entities.PointsEntity
import com.smartestidea.houseofthedargonquiz.data.database.entities.toDomain
import com.smartestidea.houseofthedargonquiz.data.model.Point
import com.smartestidea.houseofthedargonquiz.data.model.Quiz
import com.smartestidea.houseofthedargonquiz.data.model.toDomain
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizProvider:QuizProvider,
    private val rankDao: RankDao
) {
    suspend fun getRandomQuiz():MutableList<Quiz> = quizProvider.getRandomsQuiz()
    suspend fun getAllPoints():List<Point> = rankDao.getPoints()!!.map { it.toDomain()}
    suspend fun pushPoint(p:Point)= rankDao.pushPoint(p.toDomain())
    suspend fun getMaxPoint():Point= (rankDao.getMaxPoints()?:PointsEntity(0,"",0,0)).toDomain()
}