package interactor.repositories

import domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import network.auth.AuthApi
import screen.login.Result
import service.request.AuthRequest
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source
 *
 */

class LoginRepository @Inject constructor(
    private val authApi: AuthApi
) {

    var result: Result<User> = Result.Error("user is null")

    suspend fun login(login: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = authApi.authorization(AuthRequest(login, password)).execute()
            response.body()?.let {
                result = Result.Success(it.convertToKotlinClass())
            } ?: run {
                result = Result.Error("user is null")
            }
        } catch (e: Exception) {
            result = Result.Error(e.toString())
        }
        return@withContext result
    }
}
