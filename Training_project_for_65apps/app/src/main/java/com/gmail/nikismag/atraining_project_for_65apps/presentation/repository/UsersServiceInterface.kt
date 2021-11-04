package com.gmail.nikismag.atraining_project_for_65apps.presentation.repository

import com.gmail.nikismag.atraining_project_for_65apps.data.model.User

interface UsersServiceInterface {

    fun getById(userId: Long, callback: (User) -> Unit)
    fun loadUsers(callback: (List<User>) -> Unit)
}