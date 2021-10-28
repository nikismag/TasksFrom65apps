package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserListBinding
import com.gmail.nikismag.atraining_project_for_65apps.presentation.MainActivity
import com.gmail.nikismag.atraining_project_for_65apps.presentation.model.Result
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.Notifier
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.adapter.UsersAdapter
import com.gmail.nikismag.atraining_project_for_65apps.presentation.utils.navigator
import kotlinx.android.synthetic.main.fragment_user_details.*

class UsersListFragment : Fragment(), Notifier {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private var adapter: UsersAdapter? = null
    private val viewModel: UsersListViewModel by viewModels()

    override fun notifyFragment(isStarted: Boolean) {
        updateServiceState(isStarted)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionBar()
        initAdapter()

        viewModel.users.observe(viewLifecycleOwner, ::handleUsers)
        if ((requireActivity() as MainActivity).isStarted) {
            updateServiceState()
        }
        viewModel.loadUsers()
    }

    override fun onDestroyView() {
        _binding = null
        adapter = null
        super.onDestroyView()
    }

    private fun initActionBar() {
        requireActivity().setTitle(R.string.contact_list)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initAdapter() {
        adapter = UsersAdapter { navigator().showDetails(it.id) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
    }

    private fun updateServiceState(isStarted: Boolean = true) {
        (requireActivity() as MainActivity).dataService?.let { viewModel.updateService(it) }
        viewModel.onServiceStarted(isStarted)
    }

    private fun handleUsers(users: Result<List<User>>) {
        with(binding) {
            recyclerView.isVisible = users is Result.SuccessResult
            progressBar.isVisible = users !is Result.SuccessResult
            if (users is Result.SuccessResult) {
                adapter?.users = users.data
            }
        }
    }
}