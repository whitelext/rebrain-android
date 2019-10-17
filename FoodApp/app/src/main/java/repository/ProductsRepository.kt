package repository

import domain.Product
import utils.Generator

/**
 * A class that manage data from data sources
 */
class ProductsRepository(private val foodGenerator: Generator) {
    /**
     * @return [List] of [Product]
     */
    fun getProductList(): List<Product> {
        return foodGenerator.getProducts()
    }
}