package com.stas1270.githubapi.ui.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.stas1270.githubapi.databinding.ItemRepoBinding
import com.stas1270.githubapi.ui.base.BaseDiffAdapter
import com.stas1270.githubapi.ui.model.RepoModel

class RepoListAdapter(
    private val onRepoModelClick: (RepoModel) -> Unit
) : BaseDiffAdapter<RepoModel, RepoListAdapter.RepoItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemHolder {
        val binding = ItemRepoBinding.inflate(
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
        val language = "Language: ${repoModel.language ?: "Not specified"}"
        with(holder.binding) {
            itemRepoName.text = repoModel.name
            itemRepoUrl.text = repoModel.url
            itemRepoLanguage.text = language
            itemAvatar.load(repoModel.ownerAvatarUrl)
            root.setOnClickListener { onRepoModelClick(repoModel) }
        }
    }

    class RepoItemHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)
}



