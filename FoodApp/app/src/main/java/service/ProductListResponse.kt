package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductListResponse(
    @Json(name = "data")
    val productsResponse: List<ProductResponse>,
    @Json(name = "banners")
    val bannersResponse: List<BannerResponse>
)