package com.example.ezlotestapp.presentation.features.deviceList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezlotestapp.R
import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.domain.useCases.DeleteDeviceUseCase
import com.example.ezlotestapp.domain.useCases.GetAllDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val getAllDevicesUseCase: GetAllDevicesUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        getDevices(isFetching = true)
    }

    fun getDevices(isFetching: Boolean = false) {
        viewModelScope.launch {
            _state.emit(UiState.Loading)
            val data = getAllDevicesUseCase.invoke(isFetching)
            if (data.isNullOrEmpty()) {
                _state.emit(UiState.Error(message = applicationContext.getString(R.string.data_empty)))
            } else {
                _state.emit(UiState.Success(data = data))
            }
        }
    }

    fun deleteDevice(model: DeviceModel) {
        viewModelScope.launch {
            _state.emit(UiState.Loading)
            val result = async { deleteDeviceUseCase.invoke(model) }
            if (result.await() > 0) {
                getDevices()
            }
        }
    }
}