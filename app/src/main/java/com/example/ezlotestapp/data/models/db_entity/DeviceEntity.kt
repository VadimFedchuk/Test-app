package com.example.ezlotestapp.data.models.db_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "mac_address") val macAddress: String,
    @ColumnInfo(name = "firmware_name") val firmwareName: String,
    @ColumnInfo(name = "platform") val platform: String,
    @ColumnInfo(name = "image_name") val imageName: String,
)