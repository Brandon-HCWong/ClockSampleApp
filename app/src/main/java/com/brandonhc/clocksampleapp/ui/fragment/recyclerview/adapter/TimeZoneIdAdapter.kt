package com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brandonhc.clocksampleapp.databinding.ItemTimeZoneIdBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.viewholder.TimeZoneIdViewHolder

class TimeZoneIdAdapter(
    private val isSelectionEnabled: Boolean,
    private val onItemInteractionListener: OnItemInteractionListener
): ListAdapter<String, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimeZoneIdViewHolder(
            ItemTimeZoneIdBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            isSelectionEnabled,
            onItemInteractionListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TimeZoneIdViewHolder).onBind(getItem(position), position)
    }

    interface OnItemInteractionListener {
        fun onItemClick(timeZoneId: String, position: Int) {}
        fun onItemSelected(timeZoneId: String, position: Int) {}
        fun onItemUnselected(timeZoneId: String, position: Int) {}
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}