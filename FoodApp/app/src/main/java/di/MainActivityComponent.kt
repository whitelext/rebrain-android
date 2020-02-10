package di

import dagger.Component
import interactor.FavoriteListStorage
import interactor.repositories.FavoritesRepository
import interactor.repositories.ProductsRepository
import screen.main.FavouriteFragment
import screen.main.MainFragment
import screen.main.viewmodel.ProductListViewModel
import screen.main.viewmodel.ProductListViewModelFactory

@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [ProductModule::class]
)
interface MainActivityComponent {
    fun productsRepository(): ProductsRepository
    fun favoriteListRepository(): FavoritesRepository
    fun favoriteListStorage(): FavoriteListStorage
    fun productListViewModelFactory(): ProductListViewModelFactory
    fun productListViewModel(): ProductListViewModel
    fun inject(mainFragment: MainFragment)
    fun inject(favouriteFragment: FavouriteFragment)
}