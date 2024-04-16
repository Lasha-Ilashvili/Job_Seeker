package com.example.job_seeker.presentation.screen.user_jobs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.job_seeker.databinding.UserJobItemBinding
import com.example.job_seeker.presentation.model.user_jobs.UserJob

class UserJobsListAdapter :
    ListAdapter<UserJob, UserJobsListAdapter.UserJobsViewHolder>(UserJobsDiffUtil) {

    object UserJobsDiffUtil : DiffUtil.ItemCallback<UserJob>() {
        override fun areItemsTheSame(
            oldItem: UserJob,
            newItem: UserJob
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserJob,
            newItem: UserJob
        ): Boolean {
            return oldItem == newItem
        }
    }

    var onDeleteClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserJobsViewHolder {
        return UserJobsViewHolder(
            UserJobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserJobsViewHolder, position: Int) {
        holder.bind()
    }

    inner class UserJobsViewHolder(private val binding: UserJobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let {userJob->
                    onDeleteClick?.invoke(userJob.id)
                }
            }
        }

        fun bind() {
            getItem(bindingAdapterPosition)?.let { userJob ->
                with(binding) {
                    tvTitle.text = userJob.title
                }
            }
        }
    }
}