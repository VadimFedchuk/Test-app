package com.example.ezlotestapp.data.db

import androidx.room.Dao
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

    @Query("DELETE FROM deviceentity WHERE id =:id")
    suspend fun deleteDevice(id: Int): Int

    @Query("UPDATE deviceentity SET device_name =:name WHERE id =:id")
    suspend fun updateName(name: String, id: Int)

}