package com.brandonhc.clocksampleapp.ui.fragment.recyclerview.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.brandonhc.clocksampleapp.databinding.ItemTimeZoneIdBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter.TimeZoneIdAdapter

class TimeZoneIdViewHolder(
    private val binding: ItemTimeZoneIdBinding,
    private val isSelectionEnabled: Boolean,
    private val onItemInteractionListener: TimeZoneIdAdapter.OnItemInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(timeZoneId: String, position: Int) {
        with(binding) {
            root.setOnClickListener {
                onItemInteractionListener.onItemClick(timeZoneId, position)
            }
            tvTimezoneId.text = timeZoneId
            mcbCheck.isVisible = isSelectionEnabled
            mcbCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    onItemInteractionListener.onItemSelected(timeZoneId, position)
                } else {
                    onItemInteractionListener.onItemUnselected(timeZoneId, position)
                }
            }
        }
    }

}