package com.example.ezlotestapp.data.dataSources.local

import com.example.ezlotestapp.data.db.DeviceDao
import com.example.ezlotestapp.data.models.db_entity.DeviceEntity
import javax.inject.Inject

class DeviceLocalDataSource @Inject constructor(
    private val deviceDao: DeviceDao
) {

    suspend fun insertAllDevices(devices: List<DeviceEntity>) {
        deviceDao.insertAllDevice(devices)
    }

    suspend fun getAllDevices(): List<DeviceEntity> {
        return deviceDao.getAllDevice()
    }

    suspend fun deleteDevice(device: DeviceEntity) {
        deviceDao.deleteDevice(device)
    }

    suspend fun updateDeviceName(device: DeviceEntity) {
        deviceDao.updateName(device.deviceName, device.id)
    }
}