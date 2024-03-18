package com.brandonhc.clocksampleapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.brandonhc.clocksampleapp.data.repository.SharedPreferenceRepository
import com.brandonhc.clocksampleapp.data.repository.TimesRepository
import com.brandonhc.clocksampleapp.data.ui.SupportedSortingMethod

class TimesViewModel(
    private val timesRepository: TimesRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {
    var sortingMethod: SupportedSortingMethod
        get() {
            return SupportedSortingMethod.valueOf(sharedPreferenceRepository.sortingMethod)
        }
        set(value) {
            sharedPreferenceRepository.sortingMethod = value.name
        }
    var refreshRateMinutes: Int
        get() {
            return sharedPreferenceRepository.refreshRateMinutes
        }
        set(value) {
            sharedPreferenceRepository.refreshRateMinutes = value
        }

    fun setCurrentLanguage(language: String) {
        sharedPreferenceRepository.currentLanguage = language
    }

    suspend fun loadUserTimeZoneInfoList() = timesRepository.loadUserTimeZoneInfoList(
        SupportedSortingMethod.valueOf(sharedPreferenceRepository.sortingMethod)
    )
}