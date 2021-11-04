package com.gmail.nikismag.atraining_project_for_65apps.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.github.javafaker.Faker
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import java.net.HttpCookie.parse
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class UsersLocalDataSource{

    internal var users = listOf<User>()
    private val faker = Faker.instance()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadUsers() {
        IMAGES.shuffle()
        users = (1..100).map {
            User(
                id = (it).toLong(),
                name = faker.name().name(),
                phoneFirst = faker.bothify("+7(###)###-##-##"),
                photo = IMAGES[it % IMAGES.size],
                birthday = LocalDate.of(1998,11,24),
                phoneSecond = faker.bothify("+7(###)###-##-##"),
                company = faker.company().name(),
                emailFirst = faker.bothify("??????###@gmail.com"),
                emailSecond = faker.bothify("??????###@gmail.com")
            )
        }
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
    }
}