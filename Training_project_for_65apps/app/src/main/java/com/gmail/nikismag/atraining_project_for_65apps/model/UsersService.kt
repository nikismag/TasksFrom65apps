package com.gmail.nikismag.atraining_project_for_65apps.model

import com.github.javafaker.Faker
import com.gmail.nikismag.atraining_project_for_65apps.R

typealias UsersListener = (users: List<User>) -> Unit

class UsersService {

    private var users = mutableListOf<User>()

    private val listeners = mutableSetOf<UsersListener>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..10).map { User(
            id = it.toLong(),
            name = faker.name().name(),
            company = faker.company().name(),
            email = faker.bothify("??????###@gmail.com"),
            email2 = faker.bothify("??????###@gmail.com"),
            phone = faker.bothify("+7(###)###-##-##"),
            phone2 = faker.bothify("+7(###)###-##-##"),
            photo = IMAGES[it % IMAGES.size]
        ) }.toMutableList()
    }

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    companion object {
        private val IMAGES = mutableListOf(
            R.drawable.dima,
            R.drawable.egor,
            R.drawable.nikita,
            R.drawable.roma,
            R.drawable.misha,
        )

    }
}