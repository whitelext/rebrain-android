package di

import dagger.Module
import dagger.Provides
import interactor.IntroFlagStorage
import utils.SharedPreferencesHelper
import utils.Storage

@Module
class IntroFlagStorageModule {
    @Provides
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = IntroFlagStorage(prefs)
}