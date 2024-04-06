package com.example.ezlotestapp.presentation.features.deviceList

import com.example.ezlotestapp.domain.models.DeviceModel

sealed class UiState {

    data object Loading : UiState()

    data class Error(val message: String) : UiState()

    data class Success(val data: List<DeviceModel>) : UiState()
}