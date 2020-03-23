package di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.BannerRepository
import interactor.repositories.FavoritesRepository
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository
import network.products.ProductsApi
import screen.main.viewmodel.ProductListViewModel
import screen.main.viewmodel.ProductListViewModelFactory
import utils.Generator

/**
 * Provides [ProductListViewModel] for main screen
 *
 */
@Module
class ProductModule(private val fragment: Fragment, private val generator: Generator) {

    @Provides
    @PerScreen
    fun provideProductRepository(productsApi: ProductsApi): ProductsRepository =
        ProductsRepository(generator, productsApi)

    @Provides
    @PerScreen
    fun provideProductListViewModelFactory(
        productsRepository: ProductsRepository,
        productModeRepository: ProductModeRepository,
        favoritesRepository: FavoritesRepository,
        bannerRepository: BannerRepository
    ): ProductListViewModelFactory =
        ProductListViewModelFactory(productsRepository, productModeRepository, favoritesRepository,bannerRepository)

    @Provides
    @PerScreen
    fun provideProductListViewModel(productListViewModelFactory: ProductListViewModelFactory): ProductListViewModel =
        ViewModelProviders.of(
                fragment,
                productListViewModelFactory
            )
            .get(ProductListViewModel::class.java)

}