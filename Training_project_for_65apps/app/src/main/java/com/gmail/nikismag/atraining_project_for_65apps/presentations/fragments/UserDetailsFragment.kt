package com.gmail.nikismag.atraining_project_for_65apps.presentations.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.UsersServiceInterface
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {

    interface UserDetails {
        fun getUserDetails(user: User)
    }

    private var dataService: UsersServiceInterface? = null
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private val callback = object : UserDetails {
        override fun getUserDetails(user: User) {
            requireView().post {
                binding.apply {
                    tvName.text = user.name
                    ivPhoto.setImageResource(user.photo)
                    tvEmailFirst.text = user.emailFirst
                    tvPhoneFirst.text = user.phoneFirst
                    tvEmailSecond.text = user.emailSecond
                    tvPhoneSecond.text = user.phoneSecond
                    tvCompany.text = user.company
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = arguments?.getInt(ARG_USER_ID)
        userId?.let {
            dataService?.getService()?.getDetails(callback, it.toLong())
        }
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

        requireActivity().setTitle(R.string.contact_details)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        dataService = null
        super.onDetach()
    }

    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}