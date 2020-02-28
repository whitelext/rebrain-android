package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.PickupPoint

@JsonClass(generateAdapter = true)
data class PickPointResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "location")
    val location: LocationResponse,
    @Json(name = "name")
    val name: String,
    @Json(name = "workingHours")
    val workingHours: String
) : ServerResponse {
    override fun convertToKotlinClass(): PickupPoint {
        return PickupPoint(id, location.convertToKotlinClass(), name, workingHours)
    }

}