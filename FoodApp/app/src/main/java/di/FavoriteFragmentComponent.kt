package di

import dagger.Component
import screen.main.FavouriteFragment
import screen.main.viewmodel.FavoriteListViewModel

/**
 * Component for [FavouriteFragment]
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [FavoriteFragmentModule::class]
)
interface FavoriteFragmentComponent {
    fun favoriteListViewModel(): FavoriteListViewModel
    fun inject(favouriteFragment: FavouriteFragment)
}