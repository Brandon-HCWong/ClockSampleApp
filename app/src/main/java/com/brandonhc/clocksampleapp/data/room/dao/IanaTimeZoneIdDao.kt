package com.brandonhc.clocksampleapp.data.room.dao

import androidx.room.*
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneIdDbEntity

@Dao
interface IanaTimeZoneIdDao {
    @Upsert
    suspend fun upsert(entity: IanaTimeZoneIdDbEntity)

    @Query("SELECT * FROM IanaTimeZoneIdDb WHERE dbId = :dbId")
    suspend fun load(dbId: String = IanaTimeZoneIdDbEntity.UNIQUE_ID_FOR_SINGLETON): IanaTimeZoneIdDbEntity?

    @Query("DELETE FROM IanaTimeZoneIdDb")
    suspend fun clear()
}