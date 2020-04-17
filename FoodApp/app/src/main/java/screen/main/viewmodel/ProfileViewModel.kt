package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import interactor.utils.REQUEST_TOO_LARGE
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import retrofit2.HttpException
import screen.main.ImageLoadingResult
import utils.BaseViewModel
import utils.Event

/**
 * [ViewModel] for profile fragment
 *
 */
class ProfileViewModel(
    val loggedInUserRepository: LoggedInUserRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {
    private val disposables = CompositeDisposable()


    private val _loggedUserName = MutableLiveData<String>()
    val loggedUserName: LiveData<String>
        get() = _loggedUserName

    private val _imageLoadingResult = MutableLiveData<Event<ImageLoadingResult>>()
    val imageLoadingResult: LiveData<Event<ImageLoadingResult>>
        get() = _imageLoadingResult

    init {
        _loggedUserName.value = loggedInUserRepository.getLoggedUser().displayName
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    /**
     * Returns true when [Snackbar] after image upload should be showed
     */
    var isImageUploadSnackNeeded = false

    /**
     * Makes a server request to upload image to server
     * if upload is successful sets image as user avatar
     *
     */
    fun setUserImage(filePath: String) {
        viewModelScope.launch {
            _imageLoadingResult.value = Event(ImageLoadingResult(isLoading = true))
            disposables.add(
                profileRepository.setAvatar(filePath)
                    .subscribeToRequest(onNext = {
                        isImageUploadSnackNeeded = true
                        _imageLoadingResult.value =
                            Event(ImageLoadingResult(filePath, isLoading = false))
                    }, onError = { error ->
                        isImageUploadSnackNeeded = true
                        if (error is HttpException)
                            _imageLoadingResult.value = Event(
                                ImageLoadingResult(
                                    error = when (error.code()) {
                                        REQUEST_TOO_LARGE.toInt() -> R.string.image_upload_error_413
                                        else -> R.string.image_upload_error
                                    }, isLoading = false
                                )
                            )
                        else {
                            _imageLoadingResult.value = Event(
                                ImageLoadingResult(
                                    error = R.string.image_upload_error,
                                    isLoading = false
                                )
                            )
                        }
                    })
            )
        }
    }
}
