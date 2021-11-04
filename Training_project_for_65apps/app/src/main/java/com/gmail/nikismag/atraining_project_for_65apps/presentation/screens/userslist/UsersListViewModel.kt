package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.presentation.model.Result
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.base.BaseViewModel

class UsersListViewModel(
) : BaseViewModel() {

    private val _users = MediatorLiveData<Result<List<User>>>()
    val users: LiveData<Result<List<User>>> = _users

    fun loadUsers() {
        if (users.value is Result.SuccessResult) return

        if (bound.value == true) {
            _users.value = Result.PendingResult
            service?.loadUsers { users -> _users.postValue(Result.SuccessResult(users)) }
            _users.removeSource(bound)
        } else {
            _users.addSource(bound) {
                if (it) loadUsers()
            }
        }
    }
}
