package service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Product

/**
 * Response class for Json deserialization of [Product] with Moshi
 *
 */
@JsonClass(generateAdapter = true)
data class ProductResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "image")
    val imageUrl: String,
    @Json(name = "isFavorite")
    val isFavorite: Boolean
) : ServerResponse<Product> {
    override fun convertToKotlinClass(): Product {
        return Product(id, name, price, imageUrl, isFavorite)
    }

}