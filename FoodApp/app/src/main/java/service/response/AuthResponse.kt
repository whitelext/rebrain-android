package service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.User

/**
 * Response class for Json deserialization of [User] after authorization with Moshi
 *
 */
@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "accessToken")
    val accesToken: String
) : ServerResponse<User> {
    override fun convertToKotlinClass(): User {
        return User(id, name, login, avatar, accesToken)
    }
}