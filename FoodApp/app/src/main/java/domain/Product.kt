package domain

/**
 * Class that represents our product
 *
 * @property id is an ID for our Product
 * @property name is a name for our Product
 * @property imageUrl is a image resource for our Product
 */
class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val isFavorite: Boolean
)