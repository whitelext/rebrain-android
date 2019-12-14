package com.example.foodapp

import android.app.Application
import di.AppComponent
import di.AppModule
import di.DaggerAppComponent
import timber.log.Timber

/**
 * Our application
 *
 * Defined separately to properly setup Timber
 */
class FoodApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
        Timber.plant(Timber.DebugTree())
    }

    fun getAppComponent() = appComponent
}