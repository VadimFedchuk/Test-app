package com.example.ezlotestapp.di

import com.example.ezlotestapp.data.dataSources.local.DeviceLocalDataSource
import com.example.ezlotestapp.data.dataSources.remote.DeviceRemoteDataSource
import com.example.ezlotestapp.data.repository.DeviceRepositoryImpl
import com.example.ezlotestapp.data.utils.mapper.DataMapper
import com.example.ezlotestapp.domain.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDeviceRepository(
        dataMapper: DataMapper,
        remoteDataSource: DeviceRemoteDataSource,
        localDataSource: DeviceLocalDataSource
    ): DeviceRepository {
        return DeviceRepositoryImpl(
            dataMapper, localDataSource, remoteDataSource
        )
    }
}