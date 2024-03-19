package com.brandonhc.clocksampleapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.brandonhc.clocksampleapp.data.repository.SharedPreferenceRepository
import com.brandonhc.clocksampleapp.data.repository.TimesRepository
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.ui.SupportedRefreshRate
import com.brandonhc.clocksampleapp.data.ui.SupportedSortingMethod

class TimesViewModel(
    private val timesRepository: TimesRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {
    private val currentUserTimeZoneInfoList = arrayListOf<UserTimeZoneInfoDbEntity>()
    var sortingMethod: SupportedSortingMethod
        get() {
            return SupportedSortingMethod.valueOf(sharedPreferenceRepository.sortingMethod)
        }
        set(value) {
            sharedPreferenceRepository.sortingMethod = value.name
        }

    var refreshRate: SupportedRefreshRate
        get() {
            return SupportedRefreshRate.valueOf(sharedPreferenceRepository.refreshRate)
        }
        set(value) {
            sharedPreferenceRepository.refreshRate = value.name
        }

    fun setCurrentLanguage(language: String) {
        sharedPreferenceRepository.currentLanguage = language
    }

    suspend fun loadUserTimeZoneInfoList() = timesRepository.loadUserTimeZoneInfoList(
        SupportedSortingMethod.valueOf(sharedPreferenceRepository.sortingMethod)
    ).apply {
        currentUserTimeZoneInfoList.clear()
        currentUserTimeZoneInfoList.addAll(this)
    }
}