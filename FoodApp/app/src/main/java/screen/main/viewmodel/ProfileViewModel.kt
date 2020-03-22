package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import kotlinx.coroutines.launch
import screen.main.ImageLoadingResult
import interactor.utils.REQUEST_TOO_LARGE
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
    fun setUserImage(filePath: String) {
        viewModelScope.launch {
            _imageLoadingResult.value = ImageLoadingResult(isLoading = true)
            val response = profileRepository.setAvatar(filePath)
            _imageLoadingResult.value = when (response) {
                is Result.Success -> ImageLoadingResult(filePath, false)
                is Result.Error -> ImageLoadingResult(
                    error = when (response.exception) {
                        REQUEST_TOO_LARGE -> R.string.image_upload_error_413
                        else -> R.string.image_upload_error
                    },
                    isLoading = false
                )
            }
        }
    }
}
