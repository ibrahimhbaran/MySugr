package com.task.mysugr.di

import android.content.Context
import com.task.mysugr.data.BGDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): BGDatabase{
              return BGDatabase.getInstance(applicationContext)
    }

    @Provides
    fun provideBgDao(bgDatabase: BGDatabase) = bgDatabase.bloodGlucoseDao()
}