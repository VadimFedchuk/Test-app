package com.example.ezlotestapp.presentation.features.deviceDetail

sealed class DeviceDetailPageState {

    data object Loading : DeviceDetailPageState()

    data class Completed(val isSuccessful: Boolean) : DeviceDetailPageState()
}