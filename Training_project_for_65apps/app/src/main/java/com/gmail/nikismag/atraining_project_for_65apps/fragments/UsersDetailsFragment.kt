package com.gmail.nikismag.atraining_project_for_65apps.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.contract.CustomTitle
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserDetailsBinding
import com.gmail.nikismag.atraining_project_for_65apps.model.User

class UserDetailsFragment : Fragment(), CustomTitle {

    lateinit var binding: FragmentUserDetailsBinding

    private val user: User
        get() = requireArguments().getSerializable(ARG_USER) as User


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater,container,false)
        binding.textNameDetails.text = user.name
        binding.avatarDetails.setImageResource(user.photo)
        binding.textEmail1Details.text = user.email
        binding.textPhone1Details.text = user.phone
        binding.textEmail2Details.text = user.email2
        binding.textPhone2Details.text = user.phone2
        binding.textInfoDetails.text = user.company
        return binding.root
    }

    companion object {
        private const val ARG_USER = "ARG_USER"

        fun newInstance(user: User): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            fragment.arguments = bundleOf(ARG_USER to user)
            return fragment
        }
    }

    override fun getTitleRes(): Int = R.string.contactDetails
}