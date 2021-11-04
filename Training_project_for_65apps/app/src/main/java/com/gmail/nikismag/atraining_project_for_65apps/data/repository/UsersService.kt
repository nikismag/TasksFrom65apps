package com.gmail.nikismag.atraining_project_for_65apps.data.repository

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.github.javafaker.Faker
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.exceptions.UserNotFoundException
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.data.model.UserDetails
import com.gmail.nikismag.atraining_project_for_65apps.presentation.repository.UsersServiceInterface
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class UsersService : Service(), UsersServiceInterface {

    private var users = listOf<User>()
    private val faker = Faker.instance()
    private val binder = DataBinder()

    override fun onBind(intent: Intent?) = binder

    override fun loadUsers(callback: (List<User>) -> Unit) {
        val weakReference = WeakReference(callback)
        thread {
            val callbackRef = weakReference.get()
            Thread.sleep(TIME_SLEEP)
            callbackRef?.invoke(users)
        }
    }

    override fun getById(userId: Long, callback: (UserDetails) -> Unit) {
        val weakReference = WeakReference(callback)
        thread {
            val callbackRef = weakReference.get()
            val user = users.firstOrNull { it.id == userId } ?: throw UserNotFoundException()
            val userDetails = UserDetails(
                user = user,
                phoneSecond = faker.bothify("+7(###)###-##-##"),
                company = faker.company().name(),
                emailFirst = faker.bothify("??????###@gmail.com"),
                emailSecond = faker.bothify("??????###@gmail.com")
            )
            Thread.sleep(TIME_SLEEP)
            callbackRef?.invoke(userDetails)
        }
    }

    private fun loadUsers() {
        IMAGES.shuffle()
        users = (1..100).map {
            User(
                id = (it + 1).toLong(),
                name = faker.name().name(),
                phoneFirst = faker.bothify("+7(###)###-##-##"),
                photo = IMAGES[it % IMAGES.size]
            )
        }
    }

    inner class DataBinder : Binder() {
        fun getService() = this@UsersService
    }

    init {
        loadUsers()
    }

    companion object {

        private val IMAGES = mutableListOf(
            R.drawable.dima,
            R.drawable.egor,
            R.drawable.nikita,
            R.drawable.roma,
            R.drawable.misha,
        )
        private const val TIME_SLEEP: Long = 1000
    }
}
