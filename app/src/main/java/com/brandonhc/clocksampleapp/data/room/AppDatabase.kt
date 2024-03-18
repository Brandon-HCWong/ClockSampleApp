package com.brandonhc.clocksampleapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brandonhc.clocksampleapp.data.room.dao.IanaTimeZoneIdDao
import com.brandonhc.clocksampleapp.data.room.dao.IanaTimeZoneInfoDao
import com.brandonhc.clocksampleapp.data.room.dao.UserTimeZoneInfoDao
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneIdDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.IanaTimeZoneInfoDbEntity
import com.brandonhc.clocksampleapp.data.room.entity.UserTimeZoneInfoDbEntity

@Database(
    entities = [
        IanaTimeZoneIdDbEntity::class,
        IanaTimeZoneInfoDbEntity::class,
        UserTimeZoneInfoDbEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ianaTimeZoneIdDao(): IanaTimeZoneIdDao
    abstract fun ianaTimeZoneInfoDao(): IanaTimeZoneInfoDao
    abstract fun userTimeZoneInfoDao(): UserTimeZoneInfoDao

    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "clock-sample-room")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}