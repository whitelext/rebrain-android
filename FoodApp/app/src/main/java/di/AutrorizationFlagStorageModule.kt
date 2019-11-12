package di

import dagger.Module
import dagger.Provides
import interactor.AuthorizationFlagStorage
import utils.SharedPreferencesHelper
import utils.Storage

@Module
class AutrorizationFlagStorageModule {
    @Provides
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = AuthorizationFlagStorage(prefs)
}