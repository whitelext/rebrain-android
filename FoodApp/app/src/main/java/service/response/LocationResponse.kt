package service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Location

/**
 * Response class for Json deserialization of [Location] with Moshi
 *
 */
@JsonClass(generateAdapter = true)
data class LocationResponse(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "long")
    val long: Double
) : ServerResponse<Location> {
    override fun convertToKotlinClass(): Location {
        return Location(lat, long)
    }
}