package network.user

import domain.Cart
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import service.response.CartResponse
import service.response.UserResponse

/**
 * The interface which provides methods to work with user-related data
 */
interface UserApi {
    @GET("user/")
    fun getUser(@Header("X-Access-Token") token: String): Single<UserResponse>

    @Multipart
    @Headers("Content-Type: multipart/form-data")
    @PUT("user/avatar/")
    fun setUserAvatar(
        @Header("X-Access-Token") token: String,
        @Part avatar: MultipartBody.Part
    ): Single<Unit>

    @POST("order/")
    fun sendOrder(@Header("X-Access-Token") token: String, @Body cart: Cart): Single<CartResponse>
}