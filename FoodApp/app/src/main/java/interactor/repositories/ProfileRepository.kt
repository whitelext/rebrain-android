package interactor.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import network.user.UserApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import utils.Result
import java.io.File
import javax.inject.Inject

/**
 * Repository for working with profile data
 *
 */
class ProfileRepository @Inject constructor(
    val userApi: UserApi,
    val authorizationTokenRepository: AuthorizationTokenRepository
) {

    var result: Result<String> = Result.Error("user is null")

    /**
     * Uploads an image to server. Returns a [Result] with [String] that tells about image upload status
     *
     */
    suspend fun setAvatar(imagePath: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.setUserAvatar(
                token = authorizationTokenRepository.getAuthroizationToken(),
                avatar = fileToMultipart(imagePath)
            ).execute()
            response.body()?.let {
                result = Result.Success("image uploaded")
            } ?: run {
                result = Result.Error("user is null")
                if (response.code() == 413) {
                    result = Result.Error("413")
                }
            }
        } catch (e: Exception) {
            result = Result.Error(e.toString())
        }
        return@withContext result
    }


    private fun fileToMultipart(imagePath: String): MultipartBody.Part {
        val imageFile = File(imagePath)
        val requestBody = RequestBody.create("image/".toMediaTypeOrNull(), imageFile)
        return MultipartBody.Part.createFormData("avatar", imageFile.name, requestBody)
    }

}