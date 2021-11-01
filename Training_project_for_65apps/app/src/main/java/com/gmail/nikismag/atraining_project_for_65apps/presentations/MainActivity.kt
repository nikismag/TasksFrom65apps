package com.gmail.nikismag.atraining_project_for_65apps.presentations

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.Navigator
import com.gmail.nikismag.atraining_project_for_65apps.data.UsersService
import com.gmail.nikismag.atraining_project_for_65apps.data.UsersServiceInterface
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.presentations.fragments.UserDetailsFragment
import com.gmail.nikismag.atraining_project_for_65apps.presentations.fragments.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator, UsersServiceInterface {

    private var contactService: UsersService? = null
    private var contactBound: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as UsersService.DataBinder
            contactService = binder.getService()
            contactBound = true
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            contactBound = false
        }
    }

    override fun getService() = contactService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(this, UsersService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }
    }

    override fun showDetails(userId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(userId))
            .addToBackStack(null)
            .commit()

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onDestroy() {
        if (contactBound) {
            unbindService(serviceConnection)
            contactBound = false
        }
        super.onDestroy()
    }
}