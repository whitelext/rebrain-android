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
    private val productList = MutableLiveData<List<Product>>()
    private val isListGrid = MutableLiveData<Boolean>()

    init {
        productList.value = repository.getProductList()
        isListGrid.value = false
    }

    fun getProductList(): LiveData<List<Product>> = productList

    fun isListGrid(): LiveData<Boolean> = isListGrid
}