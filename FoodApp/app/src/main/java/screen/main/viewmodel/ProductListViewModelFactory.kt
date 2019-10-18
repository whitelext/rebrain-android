package screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import repository.ProductsRepository

/**
 * Factory for [ProductListViewModel]
 */
class ProductListViewModelFactory(private val repository: ProductsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductListViewModel(repository) as T
    }
}