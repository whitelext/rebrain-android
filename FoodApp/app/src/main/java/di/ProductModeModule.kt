package di

import dagger.Module
import dagger.Provides
import interactor.ProductModeStorage
import interactor.repositories.ProductModeRepository
import utils.SharedPreferencesHelper
import interactor.utils.Storage

/**
 * Provides [ProductModeRepository] for application
 *
 */
@Module
class ProductModeModule {
    @Provides
    @PerApplication
    fun provideStorage(prefs: SharedPreferencesHelper): Storage<Boolean> = ProductModeStorage(prefs)

    @Provides
    @PerApplication
    fun provideRepository(storage: ProductModeStorage): ProductModeRepository =
        ProductModeRepository(storage)
}