package interactor.repositories

import domain.User
import interactor.utils.BaseNetworkRepository
import io.reactivex.Single
import network.auth.AuthApi
import service.request.AuthRequest
import service.response.AuthResponse
import utils.Result
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source
 *
 */
class LoginRepository @Inject constructor(
    private val authApi: AuthApi
) : BaseNetworkRepository() {
    /**
     * Makes a login request to server and returns a [Result] with [User]
     *
     */
//    suspend fun login(login: String, password: String): Result<User> =
//        mapCallToResult { authApi.authorization(AuthRequest(login, password)) }
//            .convert()

    fun login(login: String, password: String): Single<AuthResponse> {
        return authApi.authorization(AuthRequest(login, password))
    }
}
