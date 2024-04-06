package com.example.ezlotestapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ezlotestapp.data.models.db_entity.DeviceEntity

@Database(entities = [DeviceEntity::class], version = 1)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}