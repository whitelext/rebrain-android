package interactor.repositories

import domain.User
import network.auth.AuthApi
import service.request.AuthRequest
import utils.Result
import utils.convert
import utils.mapCallToResult
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source
 *
 */
class LoginRepository @Inject constructor(
    private val authApi: AuthApi
) {
    /**
     * Makes a login request to server and returns a [Result] with [User]
     *
     */
    suspend fun login(login: String, password: String): Result<User> =
        mapCallToResult { authApi.authorization(AuthRequest(login, password)) }
            .convert()
}
