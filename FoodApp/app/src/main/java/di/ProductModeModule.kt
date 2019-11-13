package di

import dagger.Module
import dagger.Provides
import interactor.ProductModeStorage
import interactor.repositories.ProductModeRepository
import utils.SharedPreferencesHelper
import utils.Storage

@Module
class ProductModeModule {
    @Provides
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = ProductModeStorage(prefs)

    @Provides
    fun provideRepository(storage: ProductModeStorage): ProductModeRepository =
        ProductModeRepository(storage)
}