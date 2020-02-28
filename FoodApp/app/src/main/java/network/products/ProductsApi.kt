package network.products

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import service.PickPointResponse
import service.ProductListResponse

/**
 * The interface which provides methods to work with products
 */
interface ProductsApi {

    @GET("/products")
    fun getProducts(token: String, @Query("favorite") isFavorite: Boolean): Call<ProductListResponse>

    @GET("/pickups")
    fun getPickups(): Call<PickPointResponse>


}