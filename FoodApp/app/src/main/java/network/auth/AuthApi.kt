package network.auth

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * The interface which provides methods to work with authorization
 */
interface AuthApi {
    @POST("/auth")
    fun authorization(@Body authRequest: AuthRequest): Call<Response>

    @GET("/logout")
    fun logout(token: String): Call<Response>
}