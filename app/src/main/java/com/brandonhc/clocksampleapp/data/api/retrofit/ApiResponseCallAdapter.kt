package com.brandonhc.clocksampleapp.data.api.retrofit

import com.brandonhc.clocksampleapp.data.api.retrofit.ApiResponse
import com.brandonhc.clocksampleapp.data.api.retrofit.ApiResponseCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter(
    private val dataType: Type
) : CallAdapter<Any, Call<ApiResponse<Any>>> {

    override fun responseType(): Type = dataType

    override fun adapt(call: Call<Any>): Call<ApiResponse<Any>> {
        return ApiResponseCall(delegate = call) as Call<ApiResponse<Any>>
    }
}