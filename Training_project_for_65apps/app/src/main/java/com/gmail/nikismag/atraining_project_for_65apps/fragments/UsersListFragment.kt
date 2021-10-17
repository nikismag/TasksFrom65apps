package com.gmail.nikismag.atraining_project_for_65apps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.adapter.UserActionListener
import com.gmail.nikismag.atraining_project_for_65apps.adapter.UsersAdapter
import com.gmail.nikismag.atraining_project_for_65apps.app.App
import com.gmail.nikismag.atraining_project_for_65apps.contract.CustomTitle
import com.gmail.nikismag.atraining_project_for_65apps.contract.contract
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserListBinding
import com.gmail.nikismag.atraining_project_for_65apps.model.User
import com.gmail.nikismag.atraining_project_for_65apps.model.UsersListener
import com.gmail.nikismag.atraining_project_for_65apps.model.UsersService

class UsersListFragment : Fragment(), CustomTitle {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (requireActivity().applicationContext as App).usersService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserListBinding.inflate(inflater, container, false)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserDetails(user: User) {
                contract().launchUserDetailsScreen(user)
            }
        })

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(usersListener)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun getTitleRes(): Int = R.string.contactList
}