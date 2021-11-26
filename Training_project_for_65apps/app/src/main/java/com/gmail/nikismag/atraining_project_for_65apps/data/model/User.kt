package com.gmail.nikismag.atraining_project_for_65apps.data.model

import java.time.LocalDate

data class User(
    val id: Long,
    val photo: Int,
    val phoneFirst: String,
    val name: String,
    val birthday: LocalDate,
    val phoneSecond: String,
    val emailFirst: String,
    val emailSecond: String,
    val company: String
)