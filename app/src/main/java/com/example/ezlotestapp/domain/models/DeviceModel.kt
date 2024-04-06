package com.example.ezlotestapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceModel(
    val id: Int,
    val deviceName: String,
    val macAddress: String,
    val firmwareName: String,
    val platform: String,
    val imageResourceName: String
): Parcelable
