package com.example.ezlotestapp.data.repository

import com.example.ezlotestapp.data.dataSources.local.DeviceLocalDataSource
import com.example.ezlotestapp.data.dataSources.remote.DeviceRemoteDataSource
import com.example.ezlotestapp.data.models.db_entity.DeviceEntity
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
        val localDevices = deviceLocalDataSource.getAllDevices()
        if (isFetching) {
            val remoteData = fetchNewDevices()
            val convertedRemoteData = remoteData.mapIndexed { index, device ->
                dataMapper.toDatabaseEntity(
                    device,
                    index
                )
            }.toList()

            if (convertedRemoteData.isNotEmpty()) {
                val mergedData = if (localDevices.isNotEmpty()) {
                    mergeData(localDevices, convertedRemoteData)
                } else {
                    convertedRemoteData
                }
                deviceLocalDataSource.insertAllDevices(mergedData)
                return mergedData.map { device ->
                    dataMapper.entityToDeviceModel(device)
                }.toList()
            }
        }

        return if (localDevices.isNotEmpty()) {
            localDevices.map { device ->
                dataMapper.entityToDeviceModel(device)
            }.toList()
        } else {
            emptyList()
        }
    }

    private fun mergeData(
        localDevices: List<DeviceEntity>,
        remoteData: List<DeviceEntity>
    ): List<DeviceEntity> {
        val localMap = localDevices.associateBy { it.id }

        return remoteData.map { remoteModel ->
            localMap[remoteModel.id] ?: remoteModel
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