package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Banner

@JsonClass(generateAdapter = true)
data class BannerResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val imageUrl: String
) : ServerResponse {
    override fun convertToKotlinClass(): Banner {
        return Banner(id, imageUrl)
    }

}