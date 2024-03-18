package com.brandonhc.clocksampleapp.data.api

import com.brandonhc.clocksampleapp.data.api.interceptor.CustomLoggingInterceptor
import com.brandonhc.clocksampleapp.data.api.response.IanaTimeZoneInfoResponse
import com.brandonhc.clocksampleapp.data.api.retrofit.ApiResponseCallAdapterFactory
import com.brandonhc.playseecheat.data.api.moshi.MoshiProvider
import com.brandonhc.clocksampleapp.data.api.retrofit.ApiResponse
import net.danlew.android.joda.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TimeApiService {

    @GET("api/TimeZone/AvailableTimeZones")
    suspend fun getIanaTimeZoneIdList(): ApiResponse<List<String>>

    @GET("api/TimeZone/zone")
    suspend fun getIanaTimeZoneInfo(
        @Query("timeZone") timeZoneId: String,
    ): ApiResponse<IanaTimeZoneInfoResponse>

    companion object {
        fun create(
            okhttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder(),
            baseUrl: String = "https://timeapi.io/"
        ): TimeApiService {
            okhttpClientBuilder.apply {
                readTimeout(40, TimeUnit.SECONDS)
                writeTimeout(40, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    addInterceptor(CustomLoggingInterceptor.getLargeHttpLoggingInterceptor())
                }
            }
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okhttpClientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create(MoshiProvider.moshiDefaultIfNull))
                .addCallAdapterFactory(ApiResponseCallAdapterFactory())
                .build()
                .create(TimeApiService::class.java)
        }
    }
}