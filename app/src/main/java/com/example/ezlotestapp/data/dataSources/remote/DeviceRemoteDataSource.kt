package com.example.ezlotestapp.data.dataSources.remote

import com.example.ezlotestapp.data.models.network_entity.DeviceResponse
import com.example.ezlotestapp.data.network.DeviceApi
import retrofit2.Response
import javax.inject.Inject

class DeviceRemoteDataSource @Inject constructor(
    private val deviceApi: DeviceApi
) {

    suspend fun getDevices(): Response<DeviceResponse> {
        return deviceApi.getDevices()
    }
}