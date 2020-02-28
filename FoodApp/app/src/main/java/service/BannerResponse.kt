package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Banner

/**
 * Response class for Json deserialization of [Banner] with Moshi
 *
 */
@JsonClass(generateAdapter = true)
data class BannerResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val imageUrl: String
) : ServerResponse<Banner> {
    override fun convertToKotlinClass(): Banner {
        return Banner(id, imageUrl)
    }

}