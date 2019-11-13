package di

import dagger.Module
import dagger.Provides
import interactor.AuthorizationFlagStorage
import interactor.repositories.AuthorizationFlagRepository
import utils.SharedPreferencesHelper
import utils.Storage

@Module
class AuthorizationFlagModule {
    @Provides
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = AuthorizationFlagStorage(prefs)

    @Provides
    fun provideRepository(storage: AuthorizationFlagStorage): AuthorizationFlagRepository =
        AuthorizationFlagRepository(storage)
}