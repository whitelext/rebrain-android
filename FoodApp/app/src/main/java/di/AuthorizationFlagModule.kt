package di

import dagger.Module
import dagger.Provides
import interactor.AuthorizationFlagStorage
import interactor.repositories.AuthorizationFlagRepository
import utils.SharedPreferencesHelper
import utils.Storage

/**
 * Provides [AuthorizationFlagRepository] for application
 *
 */
@Module
class AuthorizationFlagModule {
    @Provides
    @PerApplication
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = AuthorizationFlagStorage(prefs)

    @Provides
    @PerApplication
    fun provideRepository(storage: AuthorizationFlagStorage): AuthorizationFlagRepository =
        AuthorizationFlagRepository(storage)
}