package com.bmai.myapplication.di

import android.content.Context
import androidx.room.Room
import com.bmai.myapplication.data.local.AppDatabase
import com.bmai.myapplication.data.local.BMIDao
import com.bmai.myapplication.data.local.GoalDao
import com.bmai.myapplication.data.repository.BMIRepositoryImpl
import com.bmai.myapplication.data.repository.GoalRepository
import com.bmai.myapplication.data.repository.GoalRepositoryImpl
import com.bmai.myapplication.data.repository.SettingsRepository
import com.bmai.myapplication.data.repository.SettingsRepositoryImpl
import com.bmai.myapplication.domain.repository.BMIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "vitaltrack_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBMIDao(db: AppDatabase): BMIDao = db.bmiDao()

    @Provides
    @Singleton
    fun provideGoalDao(db: AppDatabase): GoalDao = db.goalDao()

    @Provides
    @Singleton
    fun provideBMIRepository(dao: BMIDao): BMIRepository = BMIRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideGoalRepository(dao: GoalDao): GoalRepository = GoalRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository = repository
}
