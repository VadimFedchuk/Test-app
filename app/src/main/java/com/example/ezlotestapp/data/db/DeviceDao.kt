package com.example.ezlotestapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ezlotestapp.data.models.db_entity.DeviceEntity

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDevice(devices: List<DeviceEntity>)

    @Query("SELECT * FROM deviceentity")
    suspend fun getAllDevice(): List<DeviceEntity>

    @Delete
    suspend fun deleteDevice(device: DeviceEntity)

    @Query("UPDATE deviceentity SET device_name =:name WHERE id =:id")
    suspend fun updateName(name: String, id: Int)

}