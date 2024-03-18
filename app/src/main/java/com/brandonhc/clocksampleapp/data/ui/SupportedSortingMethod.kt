package com.brandonhc.clocksampleapp.data.ui

import android.content.Context
import com.brandonhc.clocksampleapp.R

enum class SupportedSortingMethod(val resId: Int) {
    ASCENDING(R.string.ascending),
    DESCENDING(R.string.descending);

    companion object {
        fun getCharSequenceArray(context: Context): Array<CharSequence> = entries.map { context.getString(it.resId) }.toTypedArray()

        fun fromIndex(index: Int) = entries[index]
    }
}