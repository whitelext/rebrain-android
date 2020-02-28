package service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import domain.User

/**
 * Response class for Json deserialization of [User] with Moshi
 *
 */
@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar")
    val avatar: String
) : ServerResponse<User> {
    override fun convertToKotlinClass(): User {
        return User(id, name, login, avatar)
    }
}