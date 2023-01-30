package com.stas1270.githubapi.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseDiffAdapter<IT, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val items: MutableList<IT> = mutableListOf()

    open fun update(newItems: List<IT>) {
        val result = DiffUtil.calculateDiff(Callback(items, newItems))
        result.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract override fun onBindViewHolder(holder: VH, position: Int)

    /**
     * Method used to basic item comparing via DiffUtil
     */
    abstract fun areItemsTheSame(oldItem: IT, newItem: IT): Boolean

    /**
     * Method used to full item comparing via DiffUtil
     */
    open fun areContentsTheSame(oldItem: IT, newItem: IT): Boolean = oldItem == newItem

    /**
     * Method used to provide change for when
     * areItemsTheSame() returns true and areContentsTheSame() returns false
     */
    open fun getChangePayload(oldItem: IT, newItem: IT): Any? = null

    override fun getItemCount() = items.size

    private inner class Callback(private val oldItems: List<IT>, private val newItems: List<IT>) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return getChangePayload(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size
    }
}
