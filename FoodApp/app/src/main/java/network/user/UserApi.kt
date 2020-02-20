package network.user

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * The interface which provides methods to work with user-related data
 */
interface UserApi {
    @GET("/user")
    fun getUser(token: String): Call<Response>

    @GET("/user/avatar")
    fun getUserAvatar(): Call<Response>

    @POST("/order")
    fun sendOrder(): Call<Response>
}