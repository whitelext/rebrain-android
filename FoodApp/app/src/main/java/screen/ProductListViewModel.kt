package screen

import androidx.lifecycle.ViewModel
import domain.Product
import repository.ProductsRepository
import utils.Generator

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