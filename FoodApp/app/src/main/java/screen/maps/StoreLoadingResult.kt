package screen.maps

import domain.PickupPoint

/**
 * Stores loading result : success (list of stores) or error message.
 */
data class StoreLoadingResult(
    val success: List<PickupPoint>? = null,
    val isLoading: Boolean = true,
    val error: Int? = null
)
