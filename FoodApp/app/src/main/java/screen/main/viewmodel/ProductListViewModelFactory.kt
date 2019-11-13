package screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository
import javax.inject.Inject

/**
 * Factory for [ProductListViewModel]
 */
class ProductListViewModelFactory @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val productModeRepository: ProductModeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductListViewModel(productsRepository,productModeRepository) as T
    }
}