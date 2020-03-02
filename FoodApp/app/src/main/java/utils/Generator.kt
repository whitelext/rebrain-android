package utils

import domain.Product

/**
 * Singleton that generates entities
 */
object Generator {

    fun getProducts(): List<Product> {
        return (1..20).map {
            Product(it, "Блюдо номер $it", it, "", false)
        }.shuffled()
    }
}