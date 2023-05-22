package com.example.hometask3

import android.app.Application
import com.example.data.di.LocalDataModule
import com.example.hometask3.di.AppComponent
import com.example.hometask3.di.DaggerAppComponent

class MainApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .localDataModule(LocalDataModule(this))
            .build()
    }
}