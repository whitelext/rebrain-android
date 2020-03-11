package service.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Class that represents request for authorization
 *
 * @property login is User's login
 * @property password is User's password
 */
@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "login")
    val login: String,
    @Json(name = "password")
    val password: String
)
