package interactor.repositories

import android.annotation.SuppressLint
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import network.user.UserApi
import utils.Result
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

/**
 * Repository foк working with profile data
 *
 */
class ProfileRepository @Inject constructor(
    val userApi: UserApi,
    val authorizationTokenRepository: AuthorizationTokenRepository
) {

    var result: Result<String> = Result.Error("user is null")

    /**
     * Uploads an image to server. Returns a [Result] with [String]
     *
     */
    suspend fun setAvatar(image: Bitmap): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.setUserAvatar(
                token = authorizationTokenRepository.getAuthroizationToken(),
                avatar = bitmapToString(image)
            ).execute()
            response.body()?.let {
                result = Result.Success("image uploaded")
            } ?: run {
                result = Result.Error("user is null")
            }
        } catch (e: Exception) {
            result = Result.Error(e.toString())
        }
        return@withContext result
    }


    @SuppressLint("NewApi")
    fun bitmapToString(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val avatarString = Base64.getEncoder().encodeToString(baos.toByteArray())
        return Base64.getEncoder().encodeToString(baos.toByteArray())
    }

}