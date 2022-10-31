package com.smartestidea.houseofthedargonquiz.ui.viewmodel

import androidx.lifecycle.*
import com.smartestidea.houseofthedargonquiz.data.model.Quiz
import com.smartestidea.houseofthedargonquiz.domain.GetRandomQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getRandomQuizUseCase: GetRandomQuizUseCase
):ViewModel(){
    var quiz = MutableLiveData<MutableList<Quiz>>()
    var time = MutableLiveData<Int>()
    var index = MutableLiveData<Int>()
    val question: LiveData<Quiz> = Transformations.map(index) {
        quiz.value?.get(it)
    }
    val point =MutableLiveData<Int>()
    fun onCreate(){
        viewModelScope.launch {
            quiz.postValue(getRandomQuizUseCase()!!)
            point.postValue(0)
            time.postValue(30)
            index.postValue(0)
        }
    }
    fun resetTime(){
        time.postValue(30)
    }

    fun addPoints(p:Int){
        point.postValue(point.value?.plus(p))
    }

    fun decreaseTime(){
            time.postValue(time.value?.minus(1))
    }

    fun nextQuestion() = index.postValue(index.value?.plus(1))



}