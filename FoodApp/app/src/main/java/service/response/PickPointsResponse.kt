package service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.PickupPoint

/**
 * Response class for Json deserialization of [PickupPoint] list  with Moshi
 *
 */
@JsonClass(generateAdapter = true)
class PickPointsResponse(
    @Json(name = "data")
    val pickPointsResponse: List<PickPointResponse>
) {
}