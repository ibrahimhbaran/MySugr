package com.task.mysugr.di

import com.task.mysugr.data.BGRepository
import com.task.mysugr.data.BGRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindBGRepository(bgRepositoryImpl: BGRepositoryImpl): BGRepository
}