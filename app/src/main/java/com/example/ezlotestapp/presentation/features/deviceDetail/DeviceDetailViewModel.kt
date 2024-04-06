package com.example.ezlotestapp.presentation.features.deviceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.domain.useCases.UpdateDeviceNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    private val updateDeviceNameUseCase: UpdateDeviceNameUseCase,
): ViewModel() {

    private val _state = MutableSharedFlow<DeviceDetailPageState>()
    val state = _state.asSharedFlow()

    fun saveChanges(model: DeviceModel) {
        viewModelScope.launch {
            _state.emit(DeviceDetailPageState.Loading)
            updateDeviceNameUseCase.invoke(model)
            _state.emit(DeviceDetailPageState.Completed(isSuccessful = true))
        }
    }
}