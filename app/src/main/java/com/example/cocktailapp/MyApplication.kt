package com.example.cocktailapp

import android.app.Application
import com.example.cocktailapp.di.AppComponent
import com.example.cocktailapp.di.DaggerAppComponent
import com.example.cocktailapp.di.DatabaseModule

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }

}