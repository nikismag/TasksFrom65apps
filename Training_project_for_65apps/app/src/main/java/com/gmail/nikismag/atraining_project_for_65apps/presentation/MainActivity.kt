package com.gmail.nikismag.atraining_project_for_65apps.presentation

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.repository.UsersService
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.Navigator
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.Notifier
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userdetalis.UserDetailsFragment
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userslist.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {

    var isStarted: Boolean = false
        private set
    var dataService: UsersService? = null
        private set

    private var callback = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isStarted = true
            dataService = (service as UsersService.DataBinder).getService()
            notifyFragment(isStarted = true)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            notifyFragment(isStarted = false)
        }

        private fun notifyFragment(isStarted: Boolean) {
            supportFragmentManager.fragments.forEach {
                if (it is Notifier) it.notifyFragment(isStarted)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initService()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }
    }

    override fun showDetails(Id: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(Id))
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return true
    }

    override fun onDestroy() {
        if (isStarted) {
            unbindService(callback)
            isStarted = false
        }
        dataService = null
        super.onDestroy()
    }

    private fun initService() {
        Intent(this, UsersService::class.java).also { intent ->
            bindService(intent, callback, BIND_AUTO_CREATE)
        }
    }
}
