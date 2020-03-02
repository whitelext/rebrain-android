package service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response class for Json deserialization of Product list  with Moshi
 *
 */
@JsonClass(generateAdapter = true)
data class ProductListResponse(
    @Json(name = "data")
    val productsResponse: List<ProductResponse>,
    @Json(name = "banners")
    val bannersResponse: List<BannerResponse>
)