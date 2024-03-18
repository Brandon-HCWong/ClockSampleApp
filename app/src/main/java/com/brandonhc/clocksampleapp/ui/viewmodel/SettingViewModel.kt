package com.brandonhc.clocksampleapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.brandonhc.clocksampleapp.data.repository.SharedPreferenceRepository
import com.brandonhc.clocksampleapp.data.repository.TimesRepository
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.ui.SupportedSortingMethod

class SettingViewModel(
    private val timesRepository: TimesRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {
    private val timeZoneIdList: ArrayList<String> = arrayListOf()

    fun getCurrentTimeZoneIdList() = timeZoneIdList

    suspend fun loadTimeZoneIdList(isNeedForcedUpdate: Boolean): List<String> {
        if (isNeedForcedUpdate || timesRepository.loadTimeZoneIdList().isEmpty()) {
            timesRepository.fetchRemoteTimeZoneIdList()
        }
        timesRepository.loadTimeZoneIdList()
            .takeIf { it.isNotEmpty() }
            ?.let { localList ->
                timeZoneIdList.clear()
                timeZoneIdList.addAll(localList)
            }
        return timeZoneIdList
    }

    suspend fun loadTimeZoneInfo(timeZoneId: String, isNeedForcedUpdate: Boolean): IanaTimeZoneInfoDbEntity? {
        if (isNeedForcedUpdate || timesRepository.loadTimeZoneInfo(timeZoneId) == null) {
            timesRepository.fetchRemoteTimeZoneInfo(timeZoneId)
        }
        return timesRepository.loadTimeZoneInfo(timeZoneId)
    }

    suspend fun saveUserTimeZoneInfo(entity: UserTimeZoneInfoDbEntity) = timesRepository.saveUserTimeZoneInfo(entity)

    suspend fun loadUserTimeZoneInfoList() = timesRepository.loadUserTimeZoneInfoList(
        SupportedSortingMethod.valueOf(sharedPreferenceRepository.sortingMethod)
    )
}