package service.request

/**
 * Class that represents request for authorization
 *
 * @property login is User's login
 * @property password is User's password
 */
data class AuthRequest(
    val login: String,
    val password: String
)
