package com.brandonhc.clocksampleapp.data.repository

import android.util.Log
import com.brandonhc.clocksampleapp.data.api.TimeApiService
import com.brandonhc.clocksampleapp.data.api.retrofit.ApiResponse
import com.brandonhc.clocksampleapp.data.room.dao.IanaTimeZoneIdDao
import com.brandonhc.clocksampleapp.data.room.dao.IanaTimeZoneInfoDao
import com.brandonhc.clocksampleapp.data.room.dao.UserTimeZoneInfoDao
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneIdDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.ui.SupportedSortingMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TimesRepository(
    private val timeApiService: TimeApiService,
    private val ianaTimeZoneIdDao: IanaTimeZoneIdDao,
    private val ianaTimeZoneInfoDao: IanaTimeZoneInfoDao,
    private val userTimeZoneInfoDao: UserTimeZoneInfoDao,
) {
    private val TAG = "TimesRepository"

    suspend fun loadTimeZoneIdList(): List<String> = withContext(Dispatchers.IO) {
        ianaTimeZoneIdDao.load()?.ianaTimeZoneIdList ?: emptyList()
    }

    suspend fun fetchRemoteTimeZoneIdList(): Unit = withContext(Dispatchers.IO) {
        when (val response = timeApiService.getIanaTimeZoneIdList()) {
            is ApiResponse.Failure -> {
                Log.d(TAG, "[TimesRepository-fetchRemoteTimeZoneList]: Failure ${response.exception}, ${response.retrofitResponse?.errorBody().toString()}")
            }
            is ApiResponse.NetworkDisconnected -> {
                Log.d(TAG, "[TimesRepository-fetchRemoteTimeZoneList]: NetworkDisconnected}")
            }
            is ApiResponse.Success -> {
                Log.d(TAG, "[TimesRepository-fetchRemoteTimeZoneList]: Success}")
                ianaTimeZoneIdDao.upsert(IanaTimeZoneIdDbEntity.generateSingletonEntity(response.data))
            }
        }
    }

    suspend fun loadTimeZoneInfo(timeZoneId: String) = withContext(Dispatchers.IO) {
        ianaTimeZoneInfoDao.load(timeZoneId)
    }

    suspend fun fetchRemoteTimeZoneInfo(timeZoneId: String): Unit = withContext(Dispatchers.IO) {
        when (val response = timeApiService.getIanaTimeZoneInfo(timeZoneId)) {
            is ApiResponse.Failure -> {
                Log.d(TAG, "[TimesRepository-fetchRemoteTimeZoneInfo]: Failure ${response.exception}, ${response.retrofitResponse?.errorBody().toString()}")
            }
            is ApiResponse.NetworkDisconnected -> {
                Log.d(TAG, "[TimesRepository-fetchRemoteTimeZoneInfo]: NetworkDisconnected}")
            }
            is ApiResponse.Success -> {
                Log.d(TAG, "[TimesRepository-fetchRemoteTimeZoneInfo]: Success}")
                val entity = IanaTimeZoneInfoDbEntity(
                    timeZoneId,
                    response.data.currentUtcOffset.seconds
                )
                ianaTimeZoneInfoDao.upsert(entity)
            }
        }
    }

    suspend fun saveUserTimeZoneInfo(entity: UserTimeZoneInfoDbEntity) = withContext(Dispatchers.IO) {
        userTimeZoneInfoDao.upsert(entity)
    }

    suspend fun deleteUserTimeZoneInfo(timeZoneId: String) = withContext(Dispatchers.IO) {
        userTimeZoneInfoDao.delete(timeZoneId)
    }

    suspend fun deleteUserTimeZoneInfo(timeZoneIdList: List<String>) = withContext(Dispatchers.IO) {
        userTimeZoneInfoDao.deleteAll(timeZoneIdList)
    }

    suspend fun loadUserTimeZoneInfoList(supportedSortingMethod: SupportedSortingMethod) = withContext(Dispatchers.IO) {
        when (supportedSortingMethod) {
            SupportedSortingMethod.ASCENDING -> {
                userTimeZoneInfoDao.loadAllOrderedByAscending()
            }
            SupportedSortingMethod.DESCENDING -> {
                userTimeZoneInfoDao.loadAllOrderedByDescending()
            }
        }
    }
}