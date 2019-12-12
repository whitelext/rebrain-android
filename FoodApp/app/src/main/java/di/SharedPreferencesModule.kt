package di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import utils.SharedPreferencesHelper

@Module
class SharedPreferencesModule {
    @Provides
    @PerApplication
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @PerApplication
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper =
        SharedPreferencesHelper(context)
}