package com.gmail.nikismag.atraining_project_for_65apps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.nikismag.atraining_project_for_65apps.contract.AppContract
import com.gmail.nikismag.atraining_project_for_65apps.fragments.UserDetailsFragment
import com.gmail.nikismag.atraining_project_for_65apps.fragments.UsersListFragment
import com.gmail.nikismag.atraining_project_for_65apps.model.User

class MainActivity : AppCompatActivity(), AppContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }
    }

    override fun launchUserDetailsScreen(user: User) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }
}