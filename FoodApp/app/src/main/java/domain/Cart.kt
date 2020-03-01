package domain

/**
 * Cart with products
 */
data class Cart(
    val id: Int,
    val products: List<Product>,
    val productsCount: Int,
    val price: Float
)