package screen.main.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import kotlinx.coroutines.launch
import screen.main.ImageLoadingResult
import utils.Result

/**
 * [ViewModel] for profile fragment
 *
 */
class ProfileViewModel(
    val loggedInUserRepository: LoggedInUserRepository,
    val profileRepository: ProfileRepository
) : ViewModel() {
    private val _loggedUserName = MutableLiveData<String>()
    val loggedUserName: LiveData<String>
        get() = _loggedUserName

    private val _imageLoadingResult = MutableLiveData<ImageLoadingResult>()
    val imageLoadingResult: LiveData<ImageLoadingResult>
        get() = _imageLoadingResult

    init {
        _loggedUserName.value = loggedInUserRepository.getLoggedUser().displayName
    }

    /**
     * Makes a server request to upload image to server
     * if upload is successful sets image as user avatar
     *
     */
    fun setUserImage(image: Bitmap) {
        viewModelScope.launch {
            val response = profileRepository.setAvatar(image)
            if (response is Result.Success) {
                _imageLoadingResult.value = ImageLoadingResult(image, false)
            } else {
                _imageLoadingResult.value =
                    ImageLoadingResult(error = R.string.image_upload_error, isLoading = false)
            }
        }
    }
}
