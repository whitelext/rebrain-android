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
    @PerScreen
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @PerScreen
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper =
        SharedPreferencesHelper(context)
}