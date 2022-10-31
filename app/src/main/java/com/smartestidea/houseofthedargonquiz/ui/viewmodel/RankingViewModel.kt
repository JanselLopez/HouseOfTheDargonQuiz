package com.smartestidea.houseofthedargonquiz.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartestidea.houseofthedargonquiz.data.model.Point
import com.smartestidea.houseofthedargonquiz.domain.GetAllPointsUseCase
import com.smartestidea.houseofthedargonquiz.domain.GetMaxPointUseCase
import com.smartestidea.houseofthedargonquiz.domain.PushPointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getAllPointsUseCase: GetAllPointsUseCase,
    private val pushPointUseCase: PushPointUseCase,
    private val getMaxPointUseCase: GetMaxPointUseCase
):ViewModel(){
    val points = MutableLiveData<List<Point>>()
    val maxPoint = MutableLiveData<Point>()
    fun onCreate(){
        viewModelScope.launch {
            points.postValue(getAllPointsUseCase()!!)
            maxPoint.postValue(getMaxPointUseCase()!!)
        }
    }
    fun pushPoint(p:Point){
        viewModelScope.launch {
            pushPointUseCase(p)
            if(p.points> maxPoint.value?.points ?: 0){
                maxPoint.postValue(p)
            }
        }
    }
}