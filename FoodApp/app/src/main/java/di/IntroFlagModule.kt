package di

import dagger.Module
import dagger.Provides
import interactor.IntroFlagStorage
import interactor.repositories.IntroFlagRepository
import utils.SharedPreferencesHelper
import utils.Storage

@Module
class IntroFlagModule {
    @Provides
    @PerScreen
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = IntroFlagStorage(prefs)

    @Provides
    @PerScreen
    fun provideRepository(storage: IntroFlagStorage): IntroFlagRepository =
        IntroFlagRepository(storage)
}