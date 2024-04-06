package com.example.ezlotestapp.data.repository

import com.example.ezlotestapp.data.dataSources.local.DeviceLocalDataSource
import com.example.ezlotestapp.data.dataSources.remote.DeviceRemoteDataSource
import com.example.ezlotestapp.data.models.network_entity.DeviceResponse
import com.example.ezlotestapp.data.utils.mapper.DataMapper
import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.domain.repository.DeviceRepository
import java.io.IOException
import javax.inject.Inject


class DeviceRepositoryImpl @Inject constructor(
    private val dataMapper: DataMapper,
    private val deviceLocalDataSource: DeviceLocalDataSource,
    private val deviceRemoteDataSource: DeviceRemoteDataSource,
): DeviceRepository {

    override suspend fun getAllDevices(isFetching: Boolean): List<DeviceModel> {
        if (isFetching) {
            val remoteData = fetchNewDevices()
            if (remoteData.isNotEmpty()) {
                deviceLocalDataSource.insertAllDevices(
                    remoteData.mapIndexed { index, device ->
                        dataMapper.toDatabaseEntity(
                            device,
                            index
                        )
                    }.toList()
                )
            }
        }

        val devices = deviceLocalDataSource.getAllDevices()
        return if (devices.isNotEmpty()) {
            devices.map { device ->
                dataMapper.entityToDeviceModel(device)
            }.toList()
        } else {
            emptyList()
        }
    }

    private suspend fun fetchNewDevices(): List<DeviceResponse.Device> {
        val response = try {
            deviceRemoteDataSource.getDevices()
        } catch (_: IOException) {
            return emptyList()
        }
        return if (response.isSuccessful) {
            response.body()?.devices ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun deleteDevice(model: DeviceModel) {
        deviceLocalDataSource.deleteDevice(dataMapper.modelToDatabaseEntity(model))
    }

    override suspend fun changeNameDevice(model: DeviceModel) {
        deviceLocalDataSource.updateDeviceName(
            dataMapper.modelToDatabaseEntity(model)
        )
    }
}