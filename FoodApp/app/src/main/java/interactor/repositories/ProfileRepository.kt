package interactor.repositories

import android.content.Context
import id.zelory.compressor.Compressor
import network.user.UserApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import utils.Result
import utils.mapCallToResult
import java.io.File
import javax.inject.Inject

/**
 * Repository for working with profile data
 *
 */
class ProfileRepository @Inject constructor(
    val userApi: UserApi,
    val authorizationTokenRepository: AuthorizationTokenRepository,
    val context: Context
) {

    /**
     * Uploads an image to server. Returns a [Result] with [String] that tells about image upload status
     *
     */
    suspend fun setAvatar(imagePath: String): Result<Unit> =
        mapCallToResult {
            val token = authorizationTokenRepository.getAuthroizationToken()
            val multipartBody = fileToMultipart(imagePath)
            userApi.setUserAvatar(token, multipartBody)
        }


    private suspend fun fileToMultipart(imagePath: String): MultipartBody.Part {
        val imageFile = Compressor.compress(context, File(imagePath))
        val requestBody = RequestBody.create(
            "image/".toMediaTypeOrNull(), imageFile
        )
        return MultipartBody.Part.createFormData("avatar", imageFile.name, requestBody)
    }

}