package com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.databinding.ItemAnalogClockBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.viewholder.AnalogClockViewHolder

class AnalogClockAdapter: ListAdapter<UserTimeZoneInfoDbEntity, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnalogClockViewHolder(
            ItemAnalogClockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AnalogClockViewHolder).onBind(getItem(position), position)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserTimeZoneInfoDbEntity>() {
            override fun areItemsTheSame(oldItem: UserTimeZoneInfoDbEntity, newItem: UserTimeZoneInfoDbEntity): Boolean {
                return oldItem.timeZoneId == newItem.timeZoneId
            }

            override fun areContentsTheSame(oldItem: UserTimeZoneInfoDbEntity, newItem: UserTimeZoneInfoDbEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}