package com.brandonhc.clocksampleapp.data.ui

import com.brandonhc.clocksampleapp.R

enum class SupportedRefreshRate(val resId: Int, val minutes: Int) {
    IN_1_MINUTE(R.id.mrb_1_minute, 1),
    IN_5_MINUTES(R.id.mrb_5_minutes, 5),
    IN_15_MINUTES(R.id.mrb_15_minutes, 15);

    companion object {
        private val DEFAULT = IN_1_MINUTE
        fun fromResIdOrDefault(resId: Int) = entries.find { it.resId == resId } ?: DEFAULT
    }
}