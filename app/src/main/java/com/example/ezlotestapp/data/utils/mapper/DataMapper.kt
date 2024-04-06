package com.example.ezlotestapp.data.utils.mapper

import com.example.ezlotestapp.data.models.db_entity.DeviceEntity
import com.example.ezlotestapp.data.models.network_entity.DeviceResponse
import com.example.ezlotestapp.domain.models.DeviceModel
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun toDatabaseEntity(device: DeviceResponse.Device, index: Int): DeviceEntity {
        val name = "Home number ${index.plus(1)}"

        return DeviceEntity(
            id = device.pKDevice ?: index,
            deviceName = name,
            macAddress = device.macAddress ?: "",
            firmwareName = device.firmware ?: "",
            platform = device.platform ?: "",
            imageName = getImageFileName(device.platform)
        )
    }

    private fun getImageFileName(platform: String?): String {
        return when (platform) {
            "Sercomm G450" -> "vera_plus_big"
            "Sercomm G550" -> "vera_secure_big"
            else -> "vera_edge_big"
        }
    }

    fun modelToDatabaseEntity(device: DeviceModel): DeviceEntity {
        return DeviceEntity(
            id = device.id,
            deviceName = device.deviceName,
            macAddress = device.macAddress,
            firmwareName = device.firmwareName,
            platform = device.platform,
            imageName = device.imageResourceName
        )
    }

    fun entityToDeviceModel(device: DeviceEntity): DeviceModel {
        return DeviceModel(
            id = device.id,
            deviceName = device.deviceName,
            macAddress = device.macAddress,
            firmwareName = device.firmwareName,
            platform = device.platform,
            imageResourceName = device.imageName
        )
    }
}