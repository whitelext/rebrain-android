package service

import com.squareup.moshi.Json

class PickPointsResponse(
    @Json(name = "data")
    val pickPointsResponse: List<PickPointResponse>
) {
}