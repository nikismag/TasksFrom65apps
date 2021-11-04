package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.nikismag.atraining_project_for_65apps.presentation.repository.UsersServiceInterface

abstract class BaseViewModel : ViewModel() {

    protected var service: UsersServiceInterface? = null
    protected var bound = MutableLiveData<Boolean>()

    fun onServiceStarted(isStarted: Boolean) {
        bound.value = isStarted
    }

    fun updateService(usersService: UsersServiceInterface) {
        service = usersService
    }

    override fun onCleared() {
        service = null
        bound.value = false
        super.onCleared()
    }
}