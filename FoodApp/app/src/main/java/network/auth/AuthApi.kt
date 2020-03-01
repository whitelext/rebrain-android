package network.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import service.request.AuthRequest
import service.response.UserResponse

/**
 * The interface which provides methods to work with authorization
 */
interface AuthApi {
    @POST("/auth")
    fun authorization(@Body authRequest: AuthRequest): Call<UserResponse>

    @GET("/logout")
    fun logout(@Header("X-Access-Token")token: String): Call<Unit>
}