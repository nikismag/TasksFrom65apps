package com.gmail.nikismag.atraining_project_for_65apps.presentations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.*
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserListBinding

class UsersListFragment : Fragment() {

    interface UsersList {
        fun getUsersList(list: List<User>)
    }

    private var navigator: Navigator? = null
    private var dataService: UsersServiceInterface? = null
    private var _binding: FragmentUserListBinding? = null
    private var adapter: UsersAdapter? = null
    private val binding get() = _binding!!

    private val usersService: UsersService
        get() = (requireActivity().applicationContext as App).usersService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setTitle(R.string.contact_list)

        adapter = UsersAdapter { user ->
            contract().showDetails(user.id)
        }

        val callback = object : UsersList {
            override fun getUsersList(list: List<User>) {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                binding.recyclerView.adapter = adapter
                adapter?.users = usersService.getListUsers()
            }
        }
        dataService?.getService()?.getList(callback)
    }

    override fun onDestroyView() {
        _binding = null
        adapter = null
        super.onDestroyView()
    }

    override fun onDetach() {
        dataService = null
        navigator = null
        super.onDetach()
    }
}