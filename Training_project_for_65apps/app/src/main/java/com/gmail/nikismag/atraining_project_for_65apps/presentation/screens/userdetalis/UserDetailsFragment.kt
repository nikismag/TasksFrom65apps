package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userdetalis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.model.UserDetails
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserDetailsBinding
import com.gmail.nikismag.atraining_project_for_65apps.presentation.MainActivity
import com.gmail.nikismag.atraining_project_for_65apps.presentation.model.Result
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.Notifier

class UserDetailsFragment : Fragment(), Notifier {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun notifyFragment(isStarted: Boolean) {
        updateServiceState(isStarted)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionBar()

        viewModel.state.observe(viewLifecycleOwner, ::handleUserResult)
        if ((requireActivity() as MainActivity).isStarted) {
            updateServiceState()
        }
        viewModel.userId = requireArguments().getLong(ARG_USER_ID)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initActionBar() {
        requireActivity().setTitle(R.string.contact_details)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateServiceState(isStarted: Boolean = true) {
        (requireActivity() as MainActivity).dataService?.let { viewModel.updateService(it) }
        viewModel.onServiceStarted(isStarted)
    }

    private fun handleUserResult(userDetails: Result<UserDetails>) {
        with(binding) {
            contentContainer.isVisible = userDetails is Result.SuccessResult
            progressBar.isVisible = userDetails !is Result.SuccessResult
            if (userDetails is Result.SuccessResult) {
                tvName.text = userDetails.data.user.name
                tvPhoneFirst.text = userDetails.data.user.phoneFirst
                ivPhoto.setImageResource(userDetails.data.user.photo)
                tvEmailFirst.text = userDetails.data.emailFirst
                tvEmailSecond.text = userDetails.data.emailSecond
                tvPhoneSecond.text = userDetails.data.phoneSecond
                tvCompany.text = userDetails.data.company
            }
        }
    }

    companion object {

        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}


