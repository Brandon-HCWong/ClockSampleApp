package com.brandonhc.clocksampleapp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceRepository(private val preference: SharedPreferences) {
    var isSortedByAscending: Boolean
        get() {
            return preference.getBoolean(PREF_KEY_IS_SORTED_BY_ASCENDING, true)
        }
        set(value) = preference.edit {
            putBoolean(PREF_KEY_IS_SORTED_BY_ASCENDING, value)
        }

    var currentLanguage: String
        get() {
            return preference.getString(PREF_KEY_CURRENT_LANGUAGE, "en") ?: "en"
        }
        set(value) = preference.edit {
            putString(PREF_KEY_CURRENT_LANGUAGE, value)
        }


    companion object {
        const val PREFERENCE_NAME = "share_preference_clock_sample"

        /**
         * Preference Key
         */
        const val PREF_KEY_IS_SORTED_BY_ASCENDING = "pref_key_is_sorted_by_ascending"
        const val PREF_KEY_CURRENT_LANGUAGE = "pref_key_current_language"
    }
}