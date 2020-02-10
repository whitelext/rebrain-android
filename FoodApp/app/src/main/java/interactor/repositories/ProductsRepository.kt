package interactor.repositories

import com.example.foodapp.R
import domain.Product
import utils.Generator
import javax.inject.Inject

/**
 * A class that manage data from data sources
 */
class ProductsRepository @Inject constructor(private val foodGenerator: Generator) {
    /**
     * @return [List] of [Product]
     */
    fun getProductList(): List<Product> {
        return foodGenerator.getProducts()
    }

    /**
     * @return [List] of pictures
     */
    fun getCarouselPictures(): List<Int> {
        return listOf(
            R.drawable.img_carousel_1,
            R.drawable.img_carousel_2,
            R.drawable.img_carousel_3,
            R.drawable.img_carousel_4,
            R.drawable.img_carousel_5,
            R.drawable.img_carousel_6,
            R.drawable.img_carousel_7,
            R.drawable.img_carousel_8,
            R.drawable.img_carousel_9,
            R.drawable.img_carousel_10
        )
    }
}