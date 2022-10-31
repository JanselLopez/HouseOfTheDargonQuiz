package com.smartestidea.houseofthedargonquiz.domain

import com.smartestidea.houseofthedargonquiz.data.QuizRepository
import com.smartestidea.houseofthedargonquiz.data.model.Point
import com.smartestidea.houseofthedargonquiz.data.model.Quiz
import javax.inject.Inject

class GetAllPointsUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke():List<Point> = repository.getAllPoints()
}