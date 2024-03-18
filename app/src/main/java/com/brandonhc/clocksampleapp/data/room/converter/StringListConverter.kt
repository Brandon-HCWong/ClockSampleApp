package com.brandonhc.clocksampleapp.data.room.converter

import androidx.room.TypeConverter
import com.brandonhc.playseecheat.data.api.moshi.MoshiProvider
import com.squareup.moshi.Types
import java.lang.reflect.Type


class StringListConverter {
    val type: Type = Types.newParameterizedType(
        List::class.java,
        String::class.java
    )

    @TypeConverter
    fun fromItemIdListToString(titleIdList: List<String>): String {
        return MoshiProvider.toJson(type, titleIdList)
    }

    @TypeConverter
    fun fromStringToItemIdList(idListJson: String): List<String> {
        return if (idListJson.isEmpty()) {
            listOf()
        } else {
            MoshiProvider.fromJson(type, idListJson)
        }
    }
}