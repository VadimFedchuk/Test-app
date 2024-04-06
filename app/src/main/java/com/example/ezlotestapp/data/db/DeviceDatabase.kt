package com.example.ezlotestapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ezlotestapp.data.models.db_entity.DeviceEntity
import com.example.ezlotestapp.data.utils.DATABASE_VERSION

@Database(entities = [DeviceEntity::class], version = DATABASE_VERSION)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}