package com.gmail.nikismag.atraining_project_for_65apps.contract

import androidx.fragment.app.Fragment
import com.gmail.nikismag.atraining_project_for_65apps.model.User

fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract {

    fun launchUserDetailsScreen(user: User)
}