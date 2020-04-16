package network.products

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import service.response.PickPointResponse
import service.response.ProductListResponse

/**
 * The interface which provides methods to work with products
 */
interface ProductsApi {
    @Headers("Content-Type: application/json")

    @GET("products/")
    fun getProducts(
        @Header("X-Access-Token") token: String,
        @Query("favorite") isFavorite: Boolean
    ): Single<ProductListResponse>

    @GET("pickups/")
    fun getPickups(@Header("X-Access-Token") token: String): Single<List<PickPointResponse>>


}