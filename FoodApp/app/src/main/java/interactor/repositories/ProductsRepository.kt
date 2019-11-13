package interactor.repositories

import domain.Product
import utils.Generator
import javax.inject.Inject

/**
 * A class that manage data from data sources
 */
class ProductsRepository@Inject constructor(private val foodGenerator: Generator) {
    /**
     * @return [List] of [Product]
     */
    fun getProductList(): List<Product> {
        return foodGenerator.getProducts()
    }
}