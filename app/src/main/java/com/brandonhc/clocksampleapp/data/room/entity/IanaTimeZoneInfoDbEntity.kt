package com.brandonhc.clocksampleapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = IanaTimeZoneInfoDbEntity.TABLE_NAME)
data class IanaTimeZoneInfoDbEntity(
    @PrimaryKey
    val timeZoneId: String,
    val utcOffsetSeconds: Int
) {
    fun toUserTimeZoneInfoDbEntity(createdAtMilliseconds: Long) =
        UserTimeZoneInfoDbEntity(
            timeZoneId,
            utcOffsetSeconds,
            createdAtMilliseconds
        )

    companion object {
        const val TABLE_NAME = "IanaTimeZoneInfoDb"
    }
}
