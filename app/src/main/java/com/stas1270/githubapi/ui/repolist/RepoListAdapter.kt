package com.stas1270.githubapi.ui.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stas1270.githubapi.databinding.RepoItemBinding
import com.stas1270.githubapi.ui.base.BaseDiffAdapter
import com.stas1270.githubapi.ui.model.RepoModel

class RepoListAdapter(
    private val onRepoModelClick: (RepoModel) -> Unit
) : BaseDiffAdapter<RepoModel, RepoListAdapter.RepoItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemHolder {
        val binding = RepoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoItemHolder(binding)
    }

    override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
        return oldItem == newItem
    }

    override fun onBindViewHolder(holder: RepoItemHolder, position: Int) {
        val repoModel = items[position]
        with(holder) {
            binding.titleHeader.text = repoModel.header
            binding.textHeader.text = repoModel.text
            binding.root.setOnClickListener { onRepoModelClick(repoModel) }
        }
    }

    class RepoItemHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root)
}



