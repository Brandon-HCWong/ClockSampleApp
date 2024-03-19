package com.brandonhc.clocksampleapp.ui.fragment.recyclerview.viewholder

import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.brandonhc.clocksampleapp.R
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.ui.SupportedRefreshRate
import com.brandonhc.clocksampleapp.databinding.ItemAnalogClockBinding
import com.brandonhc.clocksampleapp.manager.CoroutineManager
import com.brandonhc.clocksampleapp.ui.custom.AnalogClockDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class AnalogClockViewHolder(
    private val binding: ItemAnalogClockBinding,
    private val actionGetRefreshRate: () -> SupportedRefreshRate
) : RecyclerView.ViewHolder(binding.root) {
    private val TAG = "AnalogClockViewHolder"
    private val coroutineScope = CoroutineManager.getScope(this)
    private var refreshJob: Job? = null
    fun onBind(userTimeZoneInfoDbEntity: UserTimeZoneInfoDbEntity) {
        with(binding) {
            val backgroundVectorDrawable = ContextCompat.getDrawable(root.context, R.drawable.ic_analog_clock) as VectorDrawable
            val analogClockDrawable = AnalogClockDrawable(root.context.resources, backgroundVectorDrawable)

            analogClockDrawable.setTimeZone(DateTimeZone.forOffsetHours(userTimeZoneInfoDbEntity.utcOffsetSeconds / 3600))
            analogClockDrawable.start(
                DateTime()
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0)
            )

            ivAnalogClock.setImageDrawable(analogClockDrawable)
            tvTimeZoneId.text = userTimeZoneInfoDbEntity.timeZoneId
        }
    }

    fun startRefreshJob() {
        refreshJob?.cancel()
        refreshJob = coroutineScope.launch(Dispatchers.IO) {
            runCatching {
                while (true) {
                    delay(1000)
                    if (binding.ivAnalogClock.drawable is AnalogClockDrawable) {
                        val analogClockDrawable = binding.ivAnalogClock.drawable as AnalogClockDrawable
                        if (analogClockDrawable.getMinutesFromPrevious() >= actionGetRefreshRate().minutes) {
                            withContext(Dispatchers.Main) {
                                analogClockDrawable.start(
                                    DateTime()
                                        .withSecondOfMinute(0)
                                        .withMillisOfSecond(0)
                                )
                            }
                        }
                    }
                }
            }.onFailure {
                Log.d(TAG, "[TimesFragment-startRefreshJob]: Failure = $it")
            }
        }
    }

    fun stopRefreshJob() {
        refreshJob?.cancel()
    }
}