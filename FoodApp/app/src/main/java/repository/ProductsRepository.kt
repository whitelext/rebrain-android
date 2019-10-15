package repository

import domain.Product
import utils.Generator

/**
 * Data sources
 */
object ProductsRepository {
    /**
     * @return [List] of [Product]
     */
    fun getProductList(): List<Product> {
        return Generator.getProducts()
    }
}