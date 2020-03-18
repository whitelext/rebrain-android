package network.user

import domain.Cart
import retrofit2.Call
import retrofit2.http.*
import service.response.CartResponse
import service.response.AuthResponse
import service.response.UserResponse

/**
 * The interface which provides methods to work with user-related data
 */
interface UserApi {
    @GET("user/")
    fun getUser(@Header("X-Access-Token") token: String): Call<UserResponse>

    @Multipart
    @PUT("user/avatar/")
    fun setUserAvatar(
        @Header("X-Access-Token") token: String,
        @Part("avatar") avatar: String
    ): Call<Unit>

    @POST("/order")
    fun sendOrder(@Header("X-Access-Token") token: String, @Body cart: Cart): Call<CartResponse>
}