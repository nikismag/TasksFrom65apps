package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.databinding.ItemUserBinding

class UsersAdapter(
    private val onUserClicked: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding).apply {
            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onUserClicked(users[bindingAdapterPosition])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class UsersViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            tvName.text = user.name
            tvPhoneFirst.text = user.phoneFirst
            ivPhoto.setImageResource(user.photo)
        }
    }
}