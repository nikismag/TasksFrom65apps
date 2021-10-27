package com.gmail.nikismag.atraining_project_for_65apps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.adapter.UsersAdapter
import com.gmail.nikismag.atraining_project_for_65apps.app.App
import com.gmail.nikismag.atraining_project_for_65apps.contract.contract
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserListBinding
import com.gmail.nikismag.atraining_project_for_65apps.model.UsersService

class UsersListFragment : Fragment() {

    private var binding: FragmentUserListBinding? = null
    private var adapter: UsersAdapter? = null

    private val usersService: UsersService
        get() = (requireActivity().applicationContext as App).usersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserListBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle(R.string.contact_list)

        adapter = UsersAdapter { user ->
            contract().launchUserDetailsScreen(user)
        }

        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.recyclerView?.adapter = adapter

        adapter?.users = usersService.getList()
    }

    override fun onDestroyView() {
        binding = null
        adapter = null
        super.onDestroyView()
    }
}