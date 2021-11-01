package com.gmail.nikismag.atraining_project_for_65apps.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.github.javafaker.Faker
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.presentations.fragments.UserDetailsFragment
import com.gmail.nikismag.atraining_project_for_65apps.presentations.fragments.UsersListFragment
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class UsersService : Service() {

    private var users: List<User>
    private val binder = DataBinder()

    inner class DataBinder : Binder() {
        fun getService() = this@UsersService
    }

    override fun onBind(intent: Intent?) = binder

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..100).map {
            User(
                id = it.toLong(),
                name = faker.name().name(),
                company = faker.company().name(),
                emailFirst = faker.bothify("??????###@gmail.com"),
                emailSecond = faker.bothify("??????###@gmail.com"),
                phoneFirst = faker.bothify("+7(###)###-##-##"),
                phoneSecond = faker.bothify("+7(###)###-##-##"),
                photo = IMAGES[it % IMAGES.size]
            )
        }
    }

    fun getListUsers() = users

    fun getList(callback: UsersListFragment.UsersList) {
        val weakReferenceCallback = WeakReference(callback)
        thread {
            Thread.sleep(TIME_SLEEP)
            weakReferenceCallback.get()?.getUsersList(users)
        }
    }

    fun getDetails(
        callback: UserDetailsFragment.UserDetails,
        userId: Long
    ) {
        val weakReferenceCallback = WeakReference(callback)
        thread {
            Thread.sleep(TIME_SLEEP)
            weakReferenceCallback.get()?.getUserDetails(users[userId.toInt()])
        }
    }

    companion object {
        private val IMAGES = mutableListOf(
            R.drawable.dima,
            R.drawable.egor,
            R.drawable.nikita,
            R.drawable.roma,
            R.drawable.misha,
        )
        private const val TIME_SLEEP: Long = 1500
    }


}