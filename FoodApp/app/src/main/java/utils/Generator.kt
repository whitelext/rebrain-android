package utils

import com.example.foodapp.R
import domain.Product

/**
 * Singleton that generates entities
 */
object Generator {
    private val images = listOf(R.drawable.img_list_1,R.drawable.img_list_2,R.drawable.img_list_3,R.drawable.img_list_4)

    fun getProducts(): List<Product> {
        return (1..20).map {
            Product(it, "Блюдо номер $it",images[it%4])
        }.shuffled()
    }
}