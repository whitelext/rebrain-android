package service

import com.squareup.moshi.Json
import domain.PickupPoint

/**
 * Response class for Json deserialization of [PickupPoint] list  with Moshi
 *
 */
class PickPointsResponse(
    @Json(name = "data")
    val pickPointsResponse: List<PickPointResponse>
) {
}