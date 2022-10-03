package com.kerite.fission.android.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.kerite.fission.AntiShaker
import com.kerite.fission.android.ViewBindingInflateWithParent

/**
 * A Simple List Recycler Adapter with item click event antiShaker
 * 一个简单的 ListRecyclerAdapter 包括 ViewBinding 和 防止同时多个点击事件
 * @param onBind Function Called in SimpleListItemViewHolder.bind()
 * @param onItemClick Function called when item clicked
 * @param antiShake Whether multiple click event triggered in the same time
 * @author Kerite
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SimpleListRecyclerAdapter<VB : ViewBinding, D : Any>(
    private val context: Context,
    private val itemBindingInflate: ViewBindingInflateWithParent<VB>,
    private val onBind: VB.(D) -> Unit,
    var onItemClick: (D) -> Unit = {},
    var antiShake: Boolean = true
) : ListAdapter<D, SimpleListRecyclerAdapter.SimpleListItemViewHolder<VB, D>>(object : DiffUtil.ItemCallback<D>() {
    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean = oldItem == newItem
}) {
    private val antiShaker = AntiShaker()
    private val antiShakerProducer: () -> AntiShaker? = {
        if (antiShake) this.antiShaker else null
    }
    private val onItemClickProducer: () -> ((D) -> Unit) = { onItemClick }

    /**
     * View Holder of SimpleListRecyclerAdapter
     * @author Kerite
     */
    class SimpleListItemViewHolder<VB : ViewBinding, D>(
        val binding: VB,
        private val onBind: VB.(D) -> Unit,
        private val antiShakerProducer: () -> AntiShaker?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: D, onItemClickProducer: () -> ((D) -> Unit)) {
            binding.apply {
                onBind(binding, data)
                root.setOnClickListener {
                    val antiShaker = antiShakerProducer()
                    if (antiShaker == null) {
                        onItemClickProducer()(data)
                    } else {
                        antiShaker.antiShake {
                            onItemClickProducer()(data)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SimpleListItemViewHolder<VB, D> {
        return SimpleListItemViewHolder(
            itemBindingInflate(LayoutInflater.from(context), parent, false), onBind, antiShakerProducer
        )
    }

    override fun onBindViewHolder(holder: SimpleListItemViewHolder<VB, D>, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickProducer)
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        holder.binding.root.startAnimation(animation)
    }

    override fun onViewDetachedFromWindow(holder: SimpleListItemViewHolder<VB, D>) {
        holder.binding.root.clearAnimation()
    }
}