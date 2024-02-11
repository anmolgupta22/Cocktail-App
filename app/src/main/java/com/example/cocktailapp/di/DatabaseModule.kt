package com.example.cocktailapp.di

import android.app.Application
import com.example.cocktailapp.MyApplication
import com.example.cocktailapp.database.DBHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: MyApplication) {

    @Provides
    @Singleton
    fun providesDatabase(application: Application) = DBHelper.getInstance(application)

    @Provides
    @Singleton
    fun providesLocationDao(database: DBHelper) = database.characterDao()

    @Provides
    @Singleton
    fun provideApplication(): Application = application
}
