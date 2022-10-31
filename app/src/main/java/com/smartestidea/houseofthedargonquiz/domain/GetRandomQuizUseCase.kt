package com.smartestidea.houseofthedargonquiz.domain

import com.smartestidea.houseofthedargonquiz.data.QuizRepository
import com.smartestidea.houseofthedargonquiz.data.model.Quiz
import javax.inject.Inject

class GetRandomQuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke():MutableList<Quiz> = repository.getRandomQuiz()
}