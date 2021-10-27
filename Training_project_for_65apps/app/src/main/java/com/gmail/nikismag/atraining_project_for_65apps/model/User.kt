package com.gmail.nikismag.atraining_project_for_65apps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Long = 0L,
    val photo: Int = 0,
    val phoneFirst: String = "",
    val phoneSecond: String = "",
    val name: String = "",
    val emailFirst: String = "",
    val emailSecond: String = "",
    val company: String = ""
) : Parcelable
