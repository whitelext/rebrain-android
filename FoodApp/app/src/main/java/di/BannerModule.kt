package di

import dagger.Module
import dagger.Provides
import interactor.repositories.BannerRepository

/**
 * Provides [BannerRepository] for application
 *
 */
@Module
class BannerModule {
    @Provides
    @PerApplication
    fun provideBannerRepository(): BannerRepository = BannerRepository()

}