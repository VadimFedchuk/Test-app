package com.example.ezlotestapp.presentation.adapter

import com.example.ezlotestapp.domain.models.DeviceModel

interface ListItemEventListener {

    fun openDetailClick(model: DeviceModel)

    fun onLongClickToDeleteItem(model: DeviceModel)

    fun editDeviceClick(model: DeviceModel)
}