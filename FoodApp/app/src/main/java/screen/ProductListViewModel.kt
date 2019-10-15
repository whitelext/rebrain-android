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
     * @return shuffled [MutableList] of [Product]
     */
    fun shuffleProductList(): MutableList<Product> {
        return repository.getProductList().shuffled().toMutableList()
    }
}