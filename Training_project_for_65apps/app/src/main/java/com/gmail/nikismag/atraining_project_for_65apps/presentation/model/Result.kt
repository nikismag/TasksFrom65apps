package com.gmail.nikismag.atraining_project_for_65apps.presentation.model

sealed class Result<out T> {

    data class SuccessResult<out T>(val data: T) : Result<T>()
    object PendingResult : Result<Nothing>()
}
