package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.Product
import repository.ProductsRepository

/**
 * [ViewModel] for MainFragment
 */
class ProductListViewModel(private val repository: ProductsRepository) : ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    private val _isListGrid = MutableLiveData<Boolean>()
    val isListGrid: LiveData<Boolean>
        get() = _isListGrid

    init {
        _productList.value = repository.getProductList()
        _isListGrid.value = false
    }

    /**
     * @return [List] of [Product] or empty list
     */
    fun getProductList(): List<Product> {
        productList.value?.let {
            return it
        }
        return listOf()
    }

    /**
     * Shuffles productList
     * @return shuffled [List] of [Product]
     */
    fun shuffleProductList(): List<Product> {
        return repository.getProductList().shuffled()
    }
}