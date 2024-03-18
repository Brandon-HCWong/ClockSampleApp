package com.brandonhc.clocksampleapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.brandonhc.clocksampleapp.data.room.converter.StringListConverter

@Entity(tableName = IanaTimeZoneIdDbEntity.TABLE_NAME)
@TypeConverters(StringListConverter::class)
data class IanaTimeZoneIdDbEntity(
    @PrimaryKey
    val dbId: String,
    val ianaTimeZoneIdList: List<String>
) {
    companion object {
        const val TABLE_NAME = "IanaTimeZoneIdDb"
        const val UNIQUE_ID_FOR_SINGLETON = "unique_id_for_singleton"

        fun generateSingletonEntity(idList: List<String>) = IanaTimeZoneIdDbEntity(
            UNIQUE_ID_FOR_SINGLETON,
            idList
        )
    }
}
