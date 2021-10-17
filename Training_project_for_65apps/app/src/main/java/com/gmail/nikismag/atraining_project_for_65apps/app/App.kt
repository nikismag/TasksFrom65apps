package com.gmail.nikismag.atraining_project_for_65apps.app

import android.app.Application
import com.gmail.nikismag.atraining_project_for_65apps.model.UsersService


class App : Application() {

    val usersService = UsersService()
}