package com.brandonhc.clocksampleapp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.brandonhc.clocksampleapp.data.ui.SupportedRefreshRate
import com.brandonhc.clocksampleapp.data.ui.SupportedSortingMethod

class SharedPreferenceRepository(private val preference: SharedPreferences) {
    var sortingMethod: String
        get() {
            return preference.getString(PREF_KEY_SORTING_METHOD, SupportedSortingMethod.ASCENDING.name) ?: SupportedSortingMethod.ASCENDING.name
        }
        set(value) = preference.edit {
            putString(PREF_KEY_SORTING_METHOD, value)
        }

    var currentLanguage: String
        get() {
            return preference.getString(PREF_KEY_CURRENT_LANGUAGE, "en") ?: "en"
        }
        set(value) = preference.edit {
            putString(PREF_KEY_CURRENT_LANGUAGE, value)
        }

    var refreshRate: String
        get() {
            return preference.getString(PREF_KEY_REFRESH_RATE, SupportedRefreshRate.IN_1_MINUTE.name) ?: SupportedRefreshRate.IN_1_MINUTE.name
        }
        set(value) = preference.edit {
            putString(PREF_KEY_REFRESH_RATE, value)
        }

    companion object {
        const val PREFERENCE_NAME = "share_preference_clock_sample"

        /**
         * Preference Key
         */
        const val PREF_KEY_SORTING_METHOD = "pref_key_sorting_method"
        const val PREF_KEY_CURRENT_LANGUAGE = "pref_key_current_language"
        const val PREF_KEY_REFRESH_RATE = "pref_key_refresh_rate"
    }
}