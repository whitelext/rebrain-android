package di

import dagger.Module
import dagger.Provides
import interactor.IntroFlagStorage
import interactor.repositories.IntroFlagRepository
import utils.SharedPreferencesHelper
import utils.Storage

/**
 * Provides [IntroFlagRepository] for application
 *
 */
@Module
class IntroFlagModule {
    @Provides
    @PerApplication
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = IntroFlagStorage(prefs)

    @Provides
    @PerApplication
    fun provideRepository(storage: IntroFlagStorage): IntroFlagRepository =
        IntroFlagRepository(storage)
}