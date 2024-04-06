package com.example.ezlotestapp.di

import android.content.Context
import androidx.room.Room
import com.example.ezlotestapp.data.db.DeviceDao
import com.example.ezlotestapp.data.db.DeviceDatabase
import com.example.ezlotestapp.data.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DeviceDatabase {
        return Room.databaseBuilder(
            appContext,
            DeviceDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideDeviceDao(deviceDatabase: DeviceDatabase): DeviceDao {
        return deviceDatabase.deviceDao()
    }
}