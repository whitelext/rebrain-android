package screen.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUser? = null,
    val isLoading: Boolean = true,
    val error: Int? = null
)
