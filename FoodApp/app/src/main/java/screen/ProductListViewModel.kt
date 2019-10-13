package screen

import androidx.lifecycle.ViewModel
import utils.Generator

/**
 * [ViewModel] for MainFragment
 */
class ProductListViewModel :ViewModel(){
    val productList = Generator.getProducts().toMutableList()

    fun shuffleList(){
        productList.shuffle()
    }
}