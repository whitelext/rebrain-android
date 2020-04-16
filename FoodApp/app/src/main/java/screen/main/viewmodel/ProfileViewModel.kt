package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import interactor.utils.REQUEST_TOO_LARGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import screen.main.ImageLoadingResult

/**
 * [ViewModel] for profile fragment
 *
 */
class ProfileViewModel(
    val loggedInUserRepository: LoggedInUserRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()


    private val _loggedUserName = MutableLiveData<String>()
    val loggedUserName: LiveData<String>
        get() = _loggedUserName

    private val _imageLoadingResult = MutableLiveData<ImageLoadingResult>()
    val imageLoadingResult: LiveData<ImageLoadingResult>
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
            _imageLoadingResult.value = ImageLoadingResult(isLoading = true)
            disposables.add(
                profileRepository.setAvatar(filePath)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        isImageUploadSnackNeeded = true
                        _imageLoadingResult.value = ImageLoadingResult(filePath, isLoading = false)
                    },
                        { error ->
                            isImageUploadSnackNeeded = true
                            if (error is HttpException)
                                _imageLoadingResult.value = ImageLoadingResult(
                                    error = when (error.code()) {
                                        REQUEST_TOO_LARGE.toInt() -> R.string.image_upload_error_413
                                        else -> R.string.image_upload_error
                                    }, isLoading = false
                                )
                            else {
                                _imageLoadingResult.value = ImageLoadingResult(
                                    error = R.string.image_upload_error,
                                    isLoading = false
                                )
                            }
                        })
            )
        }
    }
}
