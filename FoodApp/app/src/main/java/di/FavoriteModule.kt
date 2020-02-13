package di

import dagger.Module
import dagger.Provides
import interactor.FavoriteListStorage
import interactor.repositories.FavoritesRepository


@Module
class FavoriteModule {

    @Provides
    @PerApplication
    fun provideStorage(): FavoriteListStorage = FavoriteListStorage()

    @Provides
    @PerApplication
    fun provideRepository(storage: FavoriteListStorage): FavoritesRepository =
        FavoritesRepository(storage)


}