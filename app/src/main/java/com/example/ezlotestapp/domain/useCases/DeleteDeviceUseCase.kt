package com.example.ezlotestapp.domain.useCases

import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.domain.repository.DeviceRepository
import javax.inject.Inject

class DeleteDeviceUseCase @Inject constructor(
    private val repository: DeviceRepository,
) {

    suspend operator fun invoke(model: DeviceModel): Int {
        return repository.deleteDevice(model)
    }
}