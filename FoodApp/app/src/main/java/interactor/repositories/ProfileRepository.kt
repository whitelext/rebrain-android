package interactor.repositories

import android.content.Context
import id.zelory.compressor.Compressor
import interactor.utils.BaseNetworkRepository
import io.reactivex.Single
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
    private val userApi: UserApi,
    private val authorizationTokenRepository: AuthorizationTokenRepository,
    val context: Context
) : BaseNetworkRepository() {

    /**
     * Uploads an image to server. Returns a [Result]<Unit>
     *
     */
    suspend fun setAvatar(imagePath: String): Single<Unit> {
        val token = authorizationTokenRepository.getAuthroizationToken()
        val multipartBody = fileToMultipart(imagePath)
        return userApi.setUserAvatar(token, multipartBody)
    }


    private suspend fun fileToMultipart(imagePath: String): MultipartBody.Part {
        val imageFile = Compressor.compress(context, File(imagePath))
        val requestBody = RequestBody.create(
            "image/".toMediaTypeOrNull(), imageFile
        )
        return MultipartBody.Part.createFormData("avatar", imageFile.name, requestBody)
    }

}