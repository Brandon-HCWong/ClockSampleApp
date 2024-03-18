package com.brandonhc.clocksampleapp.data.room.dao

import androidx.room.*
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneInfoDbEntity

@Dao
interface IanaTimeZoneInfoDao {
    @Upsert
    suspend fun upsert(entity: IanaTimeZoneInfoDbEntity)

    @Query("SELECT * FROM IanaTimeZoneInfoDb WHERE timeZoneId = :timeZoneId")
    suspend fun load(timeZoneId: String): IanaTimeZoneInfoDbEntity?

    @Query("DELETE FROM IanaTimeZoneInfoDb")
    suspend fun clear()
}