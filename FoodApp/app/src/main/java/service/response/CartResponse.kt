package service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Cart

/**
 * Response class for Json deserialization of [Cart] with Moshi
 */
@JsonClass(generateAdapter = true)
data class CartResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "products")
    val products: List<ProductResponse>,
    @Json(name = "productsCount")
    val productsCount: Int,
    @Json(name = "price")
    val price: Float
) : ServerResponse<Cart> {
    override fun convertToKotlinClass(): Cart {
        val products = this.products.map {
            it.convertToKotlinClass()
        }
        return Cart(id, products, productsCount, price)
    }
}

