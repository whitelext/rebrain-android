package screen.main.viewmodel

import androidx.lifecycle.ViewModel
import domain.Product
import repository.ProductsRepository

/**
 * [ViewModel] for MainFragment
 */
class ProductListViewModel(private val repository: ProductsRepository) : ViewModel() {
    /**
     * Shuffles productList
     * @return shuffled [List] of [Product]
     */
    fun shuffleProductList(): List<Product> {
        return repository.getProductList().shuffled()
    }
}