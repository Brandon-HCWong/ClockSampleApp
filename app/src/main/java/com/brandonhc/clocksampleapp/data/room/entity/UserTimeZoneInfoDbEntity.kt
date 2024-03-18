package com.brandonhc.clocksampleapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserTimeZoneInfoDbEntity.TABLE_NAME)
data class UserTimeZoneInfoDbEntity(
    @PrimaryKey
    val timeZoneId: String,
    val utcOffsetSeconds: Int,
    val createdAtMilliseconds: Long,
) {
    companion object {
        const val TABLE_NAME = "UserTimeZoneInfoDb"
    }
}
