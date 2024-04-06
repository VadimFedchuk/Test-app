package com.example.ezlotestapp.data.models.network_entity

import com.google.gson.annotations.SerializedName

data class DeviceResponse(
    @SerializedName("Devices") val devices: List<Device>? = null
) {
    data class Device(
        @SerializedName("Firmware") val firmware: String? = null,
        @SerializedName("MacAddress") val macAddress: String? = null,
        @SerializedName("Platform") val platform: String? = null,
        @SerializedName("PK_Device") val pKDevice: Int? = null,
    )
}