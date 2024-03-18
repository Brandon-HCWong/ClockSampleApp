package com.brandonhc.clocksampleapp.data.room.dao

import androidx.room.*
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity

@Dao
interface UserTimeZoneInfoDao {
    @Upsert
    suspend fun upsert(entity: UserTimeZoneInfoDbEntity)

    @Query("SELECT * FROM UserTimeZoneInfoDb WHERE timeZoneId = :timeZoneId")
    suspend fun load(timeZoneId: String): UserTimeZoneInfoDbEntity?

    @Query("SELECT * FROM UserTimeZoneInfoDb ORDER BY createdAtMilliseconds ASC")
    suspend fun loadAllOrderedByAscending(): List<UserTimeZoneInfoDbEntity>

    @Query("SELECT * FROM UserTimeZoneInfoDb ORDER BY createdAtMilliseconds DESC")
    suspend fun loadAllOrderedByDescending(): List<UserTimeZoneInfoDbEntity>

    @Query("DELETE FROM UserTimeZoneInfoDb WHERE timeZoneId = :timeZoneId")
    suspend fun delete(timeZoneId: String)

    @Query("DELETE FROM UserTimeZoneInfoDb WHERE timeZoneId IN (:timeZoneIdList)")
    suspend fun deleteAll(timeZoneIdList: List<String>)

    @Query("DELETE FROM UserTimeZoneInfoDb")
    suspend fun clear()
}