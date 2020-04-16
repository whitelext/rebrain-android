package network.auth

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import service.request.AuthRequest
import service.response.AuthResponse

/**
 * The interface which provides methods to work with authorization
 */
interface AuthApi {
    @POST("login/")
    fun authorization(@Body authRequest: AuthRequest): Single<AuthResponse>

    @GET("logout/")
    fun logout(@Header("X-Access-Token")token: String): Single<Unit>
}