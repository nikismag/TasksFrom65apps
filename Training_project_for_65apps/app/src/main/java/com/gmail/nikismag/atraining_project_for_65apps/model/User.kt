package com.gmail.nikismag.atraining_project_for_65apps.model

import java.io.Serializable

data class User(
    val id: Long,
    val photo: Int,
    val phone: String,
    val phone2: String,
    val name: String,
    val email:String,
    val email2:String,
    val company: String
): Serializable{
    override fun toString(): String {
        return name
    }
}