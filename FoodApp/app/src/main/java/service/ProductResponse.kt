package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Product

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
) : ServerResponse {
    override fun convertToKotlinClass(): Product {
        return Product(id, name, price, imageUrl, isFavorite)
    }

}