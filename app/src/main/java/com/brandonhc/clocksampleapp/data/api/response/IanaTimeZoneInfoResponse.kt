package com.brandonhc.clocksampleapp.data.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IanaTimeZoneInfoResponse(
    val timeZone: String,
    val currentUtcOffset: UtcOffset
) {
    @JsonClass(generateAdapter = true)
    data class UtcOffset(
        val seconds: Int
    )
}
