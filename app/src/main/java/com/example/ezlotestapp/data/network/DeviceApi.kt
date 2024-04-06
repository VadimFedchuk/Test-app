package com.example.ezlotestapp.data.network

import com.example.ezlotestapp.data.models.network_entity.DeviceResponse
import retrofit2.Response
import retrofit2.http.GET

interface DeviceApi {

    @GET("test_android/items.test")
    suspend fun getDevices(): Response<DeviceResponse>
}