package screen.main

import domain.User

/**
 * User loading result : success (current logged user) or error message.
 */
data class UserLoadingResult(
    val success: User? = null,
    val isLoading: Boolean = true,
    val error: Int? = null
)