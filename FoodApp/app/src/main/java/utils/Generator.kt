package utils

import domain.Product

/**#$
 * Singleton that generates entities
 */
object Generator {
    fun getProducts():List<Product>{
        return (0..19).map {
            Product(it,"Product № $it")
        }.shuffled()
    }
}