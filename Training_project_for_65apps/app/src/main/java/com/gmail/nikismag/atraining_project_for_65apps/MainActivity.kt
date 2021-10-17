package com.gmail.nikismag.atraining_project_for_65apps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gmail.nikismag.atraining_project_for_65apps.app.App
import com.gmail.nikismag.atraining_project_for_65apps.contract.AppContract
import com.gmail.nikismag.atraining_project_for_65apps.contract.CustomTitle
import com.gmail.nikismag.atraining_project_for_65apps.databinding.ActivityMainBinding
import com.gmail.nikismag.atraining_project_for_65apps.fragments.UserDetailsFragment
import com.gmail.nikismag.atraining_project_for_65apps.fragments.UsersListFragment
import com.gmail.nikismag.atraining_project_for_65apps.model.User
import com.gmail.nikismag.atraining_project_for_65apps.model.UsersService

class MainActivity : AppCompatActivity(), AppContract {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportActionBar?.title = "Contact List"

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return true
    }

    override val usersService: UsersService
        get() = (applicationContext as App).usersService

    override fun launchUserDetailsScreen(user: User) {
        val fragment = UserDetailsFragment.newInstance(user)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateUi() {
        val fragment = currentFragment

        if (fragment is CustomTitle) {
            supportActionBar?.title = getString(fragment.getTitleRes())
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }
}