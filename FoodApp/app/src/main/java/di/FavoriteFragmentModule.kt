package di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.FavoritesRepository
import screen.main.viewmodel.FavoriteListViewModel
import screen.main.viewmodel.FavoriteListViewModelFactory

@Module
class FavoriteFragmentModule(private val fragment: Fragment) {

    @Provides
    @PerScreen
    fun provideFavoriteListViewModelFactory(
        favoritesRepository: FavoritesRepository
    ): FavoriteListViewModelFactory =
        FavoriteListViewModelFactory(favoritesRepository)

    @Provides
    @PerScreen
    fun provideFavoriteListViewModel(favoriteListViewModelFactory: FavoriteListViewModelFactory): FavoriteListViewModel =
        ViewModelProviders.of(
            fragment,
            favoriteListViewModelFactory
        )
            .get(FavoriteListViewModel::class.java)

}