package com.example.ezlotestapp.domain.repository

import com.example.ezlotestapp.domain.models.DeviceModel

interface DeviceRepository {

    suspend fun getAllDevices(isFetching: Boolean): List<DeviceModel>

    suspend fun deleteDevice(model: DeviceModel)

    suspend fun changeNameDevice(model: DeviceModel)
}