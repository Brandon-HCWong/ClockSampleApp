package com.brandonhc.clocksampleapp.ui.fragment.recyclerview.viewholder

import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.brandonhc.clocksampleapp.R
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.databinding.ItemAnalogClockBinding
import com.brandonhc.clocksampleapp.ui.custom.AnalogClockDrawable
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class AnalogClockViewHolder(
    private val binding: ItemAnalogClockBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(userTimeZoneInfoDbEntity: UserTimeZoneInfoDbEntity, position: Int) {
        with(binding) {
            val backgroundVectorDrawable = ContextCompat.getDrawable(root.context, R.drawable.ic_analog_clock) as VectorDrawable
            val analogClockDrawable = AnalogClockDrawable(root.context.resources, backgroundVectorDrawable)
            analogClockDrawable.setAnimateDays(false)

            val testTimeZone = DateTimeZone.forOffsetHours(userTimeZoneInfoDbEntity.utcOffsetSeconds / 3600)
            analogClockDrawable.start(DateTime(testTimeZone).toLocalDateTime())

            ivAnalogClock.setImageDrawable(analogClockDrawable)
            tvTimeZoneId.text = userTimeZoneInfoDbEntity.timeZoneId
        }
    }

}