package com.smartestidea.houseofthedargonquiz.domain

import com.smartestidea.houseofthedargonquiz.data.QuizRepository
import com.smartestidea.houseofthedargonquiz.data.model.Point
import javax.inject.Inject

class GetMaxPointUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke():Point = repository.getMaxPoint()
}