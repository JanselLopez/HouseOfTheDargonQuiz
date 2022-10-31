package com.smartestidea.houseofthedargonquiz.core.di

import android.content.Context
import androidx.room.Room
import com.smartestidea.houseofthedargonquiz.data.database.RankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RankDatabase::class.java,"ranking_database").build()

    @Singleton
    @Provides
    fun provideJokesDao(db: RankDatabase) = db.getRankDao()
}