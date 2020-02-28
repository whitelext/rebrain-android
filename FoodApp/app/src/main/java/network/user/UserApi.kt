package network.user

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import service.UserResponse

/**
 * The interface which provides methods to work with user-related data
 */
interface UserApi {
    @GET("/user")
    fun getUser(token: String): Call<UserResponse>

    @GET("/user/avatar")
    fun getUserAvatar(): Call<UserResponse>

    @POST("/order")
    fun sendOrder(): Call<Response>
}