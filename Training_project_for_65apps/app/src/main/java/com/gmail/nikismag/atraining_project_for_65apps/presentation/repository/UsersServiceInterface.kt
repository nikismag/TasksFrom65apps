package com.gmail.nikismag.atraining_project_for_65apps.presentation.repository

import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.data.model.UserDetails

interface UsersServiceInterface {

    fun getById(userId: Long, callback: (UserDetails) -> Unit)
    fun loadUsers(callback: (List<User>) -> Unit)
}