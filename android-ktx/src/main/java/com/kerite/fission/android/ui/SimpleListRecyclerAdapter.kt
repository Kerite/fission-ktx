package com.kerite.fission.android.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.kerite.fission.android.ViewBindingInflateWithParent

@Suppress("unused")
class SimpleListRecyclerAdapter<VB : ViewBinding, D : Any>(
    private val context: Context,
    private val itemBindingInflate: ViewBindingInflateWithParent<VB>,
    private val onItemClick: (D) -> Unit,
    private val onBind: (VB, D) -> Unit
) : ListAdapter<D, SimpleListRecyclerAdapter.UniversalListItemViewHolder<VB, D>>(
    object : DiffUtil.ItemCallback<D>() {
        override fun areItemsTheSame(oldItem: D, newItem: D): Boolean = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: D, newItem: D): Boolean = oldItem == newItem
    }
) {
    class UniversalListItemViewHolder<VB : ViewBinding, D>(
        private val binding: VB,
        private val onBind: (VB, D) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: D, onClick: (D) -> Unit) {
            binding.apply {
                onBind(binding, data)
                root.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UniversalListItemViewHolder<VB, D> {
        return UniversalListItemViewHolder(
            itemBindingInflate(LayoutInflater.from(context), parent, false),
            onBind
        )
    }

    override fun onBindViewHolder(holder: UniversalListItemViewHolder<VB, D>, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }
}