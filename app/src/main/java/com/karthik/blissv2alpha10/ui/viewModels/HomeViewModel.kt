package com.karthik.blissv2alpha10.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val current = MutableLiveData<Int>()
    private val search = MutableLiveData<Int>()

    fun getCurrent() : LiveData<Int> {
        return current
    }

    fun setCurrent(curr: Int) {
        current.value = curr
    }

    fun getSearch() : LiveData<Int> {
        return search
    }

    fun setSearch(s : Int) {
        search.value = s
    }

    init {
        current.value = 0
        search.value = -1
    }
}