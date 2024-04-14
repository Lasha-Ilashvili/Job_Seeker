package com.example.job_seeker.presentation.screen.jobs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.job_seeker.databinding.JobItemBinding
import com.example.job_seeker.presentation.model.jobs.Job

class JobsListAdapter :
    PagingDataAdapter<Job, JobsListAdapter.TransactionsViewHolder>(
        TransactionsDiffUtil
    ) {

    object TransactionsDiffUtil : DiffUtil.ItemCallback<Job>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder(
            JobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind()
    }

    inner class TransactionsViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            getItem(bindingAdapterPosition)?.let { job ->
                with(binding) {

                }
            }
        }
    }
}