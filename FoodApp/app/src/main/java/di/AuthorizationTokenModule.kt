package di

import dagger.Module
import dagger.Provides
import interactor.AuthorizationTokenStorage
import interactor.repositories.AuthorizationTokenRepository
import utils.SharedPreferencesHelper
import utils.Storage

/**
 * Provides [AuthorizationTokenRepository] for application
 *
 */
@Module
class AuthorizationTokenModule {
    @Provides
    @PerApplication
    fun provideStorage(prefs: SharedPreferencesHelper): Storage<String> =
        AuthorizationTokenStorage(prefs)

    @Provides
    @PerApplication
    fun provideRepository(storage: AuthorizationTokenStorage): AuthorizationTokenRepository =
        AuthorizationTokenRepository(storage)
}