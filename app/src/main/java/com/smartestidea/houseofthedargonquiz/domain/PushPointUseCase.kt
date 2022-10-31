package com.smartestidea.houseofthedargonquiz.domain

import com.smartestidea.houseofthedargonquiz.data.QuizRepository
import com.smartestidea.houseofthedargonquiz.data.model.Point
import javax.inject.Inject

class PushPointUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(p:Point) = repository.pushPoint(p)
}