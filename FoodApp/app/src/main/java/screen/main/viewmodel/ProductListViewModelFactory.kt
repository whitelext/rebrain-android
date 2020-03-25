package screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.BannerRepository
import interactor.repositories.FavoritesRepository
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository
import javax.inject.Inject

/**
 * Factory for [ProductListViewModel]
 */
@Suppress("UNCHECKED_CAST")
class ProductListViewModelFactory @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val productModeRepository: ProductModeRepository,
    private val favoritesRepository: FavoritesRepository,
    private val bannerRepository: BannerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductListViewModel(
            productsRepository,
            productModeRepository,
            favoritesRepository,
            bannerRepository
        ) as T
    }
}