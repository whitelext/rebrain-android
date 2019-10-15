package repository

import domain.Product
import utils.Generator

/**
 * A class that manage data that gets sent to the ViewModel
 */
object ProductsRepository {
    /**
     * @return [List] of [Product]
     */
    fun getProductList(): List<Product> {
        return Generator.getProducts()
    }
}