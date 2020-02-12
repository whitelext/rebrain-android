package di

import dagger.Component
import screen.main.FavouriteFragment
import screen.main.viewmodel.FavoriteListViewModel
import screen.main.viewmodel.FavoriteListViewModelFactory

@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [FavoriteFragmentModule::class]
)
interface FavoriteFragmentComponent {
    fun favoriteViewModelFactory(): FavoriteListViewModelFactory
    fun favoriteListViewModel(): FavoriteListViewModel
    fun inject(favouriteFragment: FavouriteFragment)
}