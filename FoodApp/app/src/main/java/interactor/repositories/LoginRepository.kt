package interactor.repositories

import domain.User
import interactor.utils.BaseNetworkRepository
import io.reactivex.Single
import network.auth.AuthApi
import screen.login.LoggedInUser
import service.request.AuthRequest
import utils.Result
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source
 *
 */
class LoginRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authorizationTokenRepository: AuthorizationTokenRepository,
    private val authorizationFlagRepository: AuthorizationFlagRepository,
    private val loggedInUserRepository: LoggedInUserRepository
) : BaseNetworkRepository() {
    /**
     * Makes a login request to server and returns a [Result] with [User]
     *
     */
    fun login(login: String, password: String): Single<User> {
        return authApi.authorization(AuthRequest(login, password))
            .convert()
            .doOnSuccess { saveLoggedUser(it) }
    }


    private fun saveLoggedUser(user: User) {
        authorizationTokenRepository.saveAuthorizationToken(user.accesToken)
        authorizationFlagRepository.loginUser()
        loggedInUserRepository.setLoggedUser(LoggedInUser(user.name,user.avatar))
    }
}
