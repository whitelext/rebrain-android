package com.example.foodapp

import android.app.Application
import timber.log.Timber

/**
 * Our application
 *
 * Defined separately to properly setup Timber
 */
class FoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}