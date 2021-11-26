package com.gmail.nikismag.atraining_project_for_65apps.data.repository

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.annotation.RequiresApi
import com.gmail.nikismag.atraining_project_for_65apps.data.exceptions.UserNotFoundException
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.presentation.repository.UsersServiceInterface
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class UsersService : Service(), UsersServiceInterface {

    private val binder = DataBinder()

    @RequiresApi(Build.VERSION_CODES.O)
    private val usersLocalDataSource = UsersLocalDataSource()

    override fun onBind(intent: Intent?) = binder

    @RequiresApi(Build.VERSION_CODES.O)
    override fun loadUsers(callback: (List<User>) -> Unit) {
        val weakReference = WeakReference(callback)
        thread {
            val callbackRef = weakReference.get()
            Thread.sleep(TIME_SLEEP)
            callbackRef?.invoke(usersLocalDataSource.users)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getById(userId: Long, callback: (User) -> Unit) {
        val weakReference = WeakReference(callback)
        thread {
            val callbackRef = weakReference.get()
            val user = usersLocalDataSource.users.firstOrNull { it.id == userId }
                ?: throw UserNotFoundException()
            Thread.sleep(TIME_SLEEP)
            callbackRef?.invoke(user)
        }
    }

    inner class DataBinder : Binder() {

        fun getService() = this@UsersService
    }

    companion object {

        private const val TIME_SLEEP: Long = 1000
    }
}
