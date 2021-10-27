package com.gmail.nikismag.atraining_project_for_65apps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserDetailsBinding
import com.gmail.nikismag.atraining_project_for_65apps.model.User

class UserDetailsFragment : Fragment() {

    private var binding: FragmentUserDetailsBinding? = null

    private val user: User
        get() = requireArguments().getParcelable(ARG_USER) ?: User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle(R.string.contact_details)

        binding?.apply {
            tvName.text = user.name
            ivPhoto.setImageResource(user.photo)
            tvEmailFirst.text = user.emailFirst
            tvPhoneFirst.text = user.phoneFirst
            tvEmailSecond.text = user.emailSecond
            tvPhoneSecond.text = user.phoneSecond
            tvCompany.text = user.company
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_USER = "ARG_USER"

        fun newInstance(user: User) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER to user)
        }
    }
}