package network.products

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import service.response.PickPointsResponse
import service.response.ProductListResponse

/**
 * The interface which provides methods to work with products
 */
interface ProductsApi {
    @Headers("Content-Type: application/json")

    @GET("/products")
    fun getProducts(
        @Header("X-Access-Token") token: String,
        @Query("favorite") isFavorite: Boolean
    ): Call<ProductListResponse>

    @GET("/pickups")
    fun getPickups(): Call<PickPointsResponse>


}