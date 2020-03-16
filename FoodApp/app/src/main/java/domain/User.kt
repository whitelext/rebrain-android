package domain

/**
 * User of application
 *
 */
class User(
    val id: Int = -1,
    val name: String = "",
    val login: String = "",
    val avatar: String? = "",
    val accesToken: String = ""
)