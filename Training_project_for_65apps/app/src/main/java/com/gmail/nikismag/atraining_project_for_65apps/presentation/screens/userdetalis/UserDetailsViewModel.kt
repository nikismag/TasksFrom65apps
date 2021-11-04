package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userdetalis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gmail.nikismag.atraining_project_for_65apps.data.model.UserDetails
import com.gmail.nikismag.atraining_project_for_65apps.presentation.model.Result
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.base.BaseViewModel

class UserDetailsViewModel : BaseViewModel() {

    private val _state = MediatorLiveData<Result<UserDetails>>()
    val state: LiveData<Result<UserDetails>> = _state

    var userId: Long? = null
        set(value) {
            if (value != null && value != field) {
                field = value
                getById(value)
            }
        }

    private fun getById(userId: Long) {
        if (bound.value == true) {
            _state.value = Result.PendingResult
            service?.getById(userId) { userDetails ->
                _state.postValue(Result.SuccessResult(userDetails))
            }
            _state.removeSource(bound)
        } else {
            _state.addSource(bound) {
                if (it) getById(userId)
            }
        }
    }
}