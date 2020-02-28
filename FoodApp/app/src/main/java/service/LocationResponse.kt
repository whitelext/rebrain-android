package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.Location

@JsonClass(generateAdapter = true)
data class LocationResponse(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "long")
    val long: Double
) : ServerResponse {
    override fun convertToKotlinClass(): Location {
        return Location(lat, long)
    }
}