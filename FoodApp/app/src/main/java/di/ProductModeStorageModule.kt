package di

import dagger.Module
import dagger.Provides
import interactor.ProductModeStorage
import utils.SharedPreferencesHelper
import utils.Storage

@Module
class ProductModeStorageModule {
    @Provides
    fun provideStorage(prefs: SharedPreferencesHelper): Storage = ProductModeStorage(prefs)
}