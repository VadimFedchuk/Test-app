package com.example.ezlotestapp.domain.useCases

import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.domain.repository.DeviceRepository
import javax.inject.Inject

class GetAllDevicesUseCase @Inject constructor(
    private val repository: DeviceRepository
) {

    suspend operator fun invoke(isFetching: Boolean): List<DeviceModel>? {
        val devices = repository.getAllDevices(isFetching)
        if (devices.isEmpty()) return null
        return devices.sortedBy { device ->
            device.id
        }
    }
}