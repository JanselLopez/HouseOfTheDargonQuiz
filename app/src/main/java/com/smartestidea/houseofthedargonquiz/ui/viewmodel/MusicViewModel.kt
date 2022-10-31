package com.smartestidea.houseofthedargonquiz.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel:ViewModel() {
    val playMusic = MutableLiveData<Boolean>()
}