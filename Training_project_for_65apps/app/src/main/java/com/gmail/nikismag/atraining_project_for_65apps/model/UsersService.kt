package com.gmail.nikismag.atraining_project_for_65apps.model

import com.github.javafaker.Faker
import com.gmail.nikismag.atraining_project_for_65apps.R

class UsersService {

    private var users: List<User>

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

    fun getList() = users

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