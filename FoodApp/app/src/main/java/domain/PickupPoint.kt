package domain

/**
 * Point where an order can be
 *
 */
class PickupPoint(
    val id: Int,
    val location: Location,
    val name: String,
    val workingHours: String
)