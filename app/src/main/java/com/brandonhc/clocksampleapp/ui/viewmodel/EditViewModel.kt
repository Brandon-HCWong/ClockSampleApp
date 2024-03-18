package com.brandonhc.clocksampleapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.brandonhc.clocksampleapp.data.repository.TimesRepository
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity

class EditViewModel(
    private val timesRepository: TimesRepository
) : ViewModel() {
    private val timeZoneIdList: ArrayList<String> = arrayListOf()
    private val selectedTimeZoneIdList: ArrayList<String> = arrayListOf()

    fun selectTimeZoneId(timeZoneId: String) {
        if (!selectedTimeZoneIdList.contains(timeZoneId)) {
            selectedTimeZoneIdList.add(timeZoneId)
        }
    }

    fun unselectTimeZoneId(timeZoneId: String) {
        selectedTimeZoneIdList.remove(timeZoneId)
    }

    fun getSelectedTimeZoneIdList() = selectedTimeZoneIdList

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

    suspend fun deleteUserTimeZoneInfo(timeZoneId: String) = timesRepository.deleteUserTimeZoneInfo(timeZoneId)

    suspend fun deleteUserTimeZoneInfo(timeZoneIdList: List<String>) = timesRepository.deleteUserTimeZoneInfo(timeZoneIdList)

    suspend fun loadUserTimeZoneInfoList(isOrderedByAscending: Boolean) = timesRepository.loadUserTimeZoneInfoList(isOrderedByAscending)
}