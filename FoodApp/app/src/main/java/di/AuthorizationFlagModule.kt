package di

import dagger.Module
import dagger.Provides
import interactor.AuthorizationFlagStorage
import interactor.repositories.AuthorizationFlagRepository
import utils.SharedPreferencesHelper
import interactor.utils.Storage

/**
 * Provides [AuthorizationFlagRepository] for application
 *
 */
@Module
class AuthorizationFlagModule {
    @Provides
    @PerApplication
    fun provideStorage(prefs: SharedPreferencesHelper): Storage<Boolean> =
        AuthorizationFlagStorage(prefs)

    @Provides
    @PerApplication
    fun provideRepository(storage: AuthorizationFlagStorage): AuthorizationFlagRepository =
        AuthorizationFlagRepository(storage)
}