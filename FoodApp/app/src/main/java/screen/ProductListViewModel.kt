package screen

import androidx.lifecycle.ViewModel
import domain.Product
import utils.Generator

/**
 * [ViewModel] for MainFragment
 */
class ProductListViewModel :ViewModel(){
    val productList = Generator.getProducts().toMutableList()

    /**
     * Shuffles [productList]
     * @return shuffled [productList]
     */
    fun shuffleProductList() : MutableList<Product>{
       return productList.shuffled().toMutableList()
    }
}