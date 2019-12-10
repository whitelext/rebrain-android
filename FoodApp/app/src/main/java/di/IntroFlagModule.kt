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
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = IntroFlagStorage(prefs)

    @Provides
    fun provideRepository(storage: IntroFlagStorage): IntroFlagRepository =
        IntroFlagRepository(storage)
}