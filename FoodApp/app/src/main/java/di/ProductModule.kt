package di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.FavoriteListStorage
import interactor.repositories.FavoritesRepository
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository
import screen.main.viewmodel.ProductListViewModel
import screen.main.viewmodel.ProductListViewModelFactory
import utils.Generator

@Module
class ProductModule(private val fragment: Fragment, private val generator: Generator) {

    @Provides
    @PerScreen
    fun provideProductRepository(): ProductsRepository =
        ProductsRepository(generator)

    @Provides
    @PerScreen
    fun provideProductListViewModelFactory(
        productsRepository: ProductsRepository,
        productModeRepository: ProductModeRepository,
        favoritesRepository: FavoritesRepository
    ): ProductListViewModelFactory =
        ProductListViewModelFactory(productsRepository, productModeRepository, favoritesRepository)

    @Provides
    @PerScreen
    fun provideProductListViewModel(productListViewModelFactory: ProductListViewModelFactory): ProductListViewModel =
        ViewModelProviders.of(
            fragment,
            productListViewModelFactory
        )
            .get(ProductListViewModel::class.java)

}