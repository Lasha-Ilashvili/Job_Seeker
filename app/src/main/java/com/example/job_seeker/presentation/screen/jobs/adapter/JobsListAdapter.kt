package com.example.job_seeker.presentation.screen.jobs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.job_seeker.databinding.JobItemBinding
import com.example.job_seeker.presentation.model.jobs.Job

class JobsListAdapter : PagingDataAdapter<Job, JobsListAdapter.JobsViewHolder>(JobsDiffUtil) {

    object JobsDiffUtil : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(
            oldItem: Job,
            newItem: Job
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Job,
            newItem: Job
        ): Boolean {
            return oldItem == newItem
        }
    }

    var onClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        return JobsViewHolder(
            JobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        holder.bind()
    }

    inner class JobsViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let {
                    onClick?.invoke(it.id)
                }
            }
        }

        fun bind() {
            getItem(bindingAdapterPosition)?.let { job ->
                with(binding) {
                    jobItem.btnDelete.isVisible = false
                    jobItem.tvJob.text = job.title
                    jobItem.tvCompany.text = job.company
                    tvSalary.text = job.salary
                }
            }
        }
    }
}