package com.gmail.nikismag.atraining_project_for_65apps.data

import androidx.fragment.app.Fragment

fun Fragment.contract(): Navigator = requireActivity() as Navigator

interface Navigator {
    fun showDetails(userId: Long)
}