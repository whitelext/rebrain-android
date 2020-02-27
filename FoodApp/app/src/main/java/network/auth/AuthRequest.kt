package network.auth

/**
 * Class that represents request for authorization
 *
 * @property login is User's login
 * @property password is User's password
 */
class AuthRequest(
    val login: String,
    val password: String
)
