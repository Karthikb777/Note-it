package com.karthik.blissv2alpha10.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val current = MutableLiveData<Int>()

    fun getCurrent() : LiveData<Int> {
        return current
    }

    fun setCurrent(curr: Int) {
        current.value = curr
    }

    init {
        current.value = 0
    }
}