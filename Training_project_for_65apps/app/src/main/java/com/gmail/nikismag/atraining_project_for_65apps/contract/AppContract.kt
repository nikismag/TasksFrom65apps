package com.gmail.nikismag.atraining_project_for_65apps.contract

import androidx.fragment.app.Fragment
import com.gmail.nikismag.atraining_project_for_65apps.model.User
import com.gmail.nikismag.atraining_project_for_65apps.model.UsersService


fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract {

    val usersService: UsersService

    fun launchUserDetailsScreen(user: User)

}