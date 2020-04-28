package com.mokresh.analyticsdashboard.ui

import androidx.lifecycle.ViewModel
import com.mokresh.analyticsdashboard.data.Repository

class ViewModel(
    private val repository: Repository
) : ViewModel() {


    override fun onCleared() {
        repository.cleared()
    }

}