package network.products

import retrofit2.Call
import retrofit2.http.*
import service.request.AuthRequest
import service.response.PickPointsResponse
import service.response.ProductListResponse
import service.response.UserResponse

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

    @POST("login/")
    fun authorization(@Body authRequest: AuthRequest): Call<UserResponse>


}