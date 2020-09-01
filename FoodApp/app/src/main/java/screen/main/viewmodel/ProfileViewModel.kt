package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import interactor.utils.REQUEST_TOO_LARGE
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import retrofit2.HttpException
import screen.main.ImageLoadingResult
import screen.main.UserLoadingResult
import utils.BaseViewModel
import utils.Event

/**
 * [ViewModel] for profile fragment
 *
 */
class ProfileViewModel(
    val loggedInUserRepository: LoggedInUserRepository,
    val authorizationFlagRepository: AuthorizationFlagRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {
    private val disposables = CompositeDisposable()


    private val _loggedUserName = MutableLiveData<String>()
    val loggedUserName: LiveData<String>
        get() = _loggedUserName

    private val _imageLoadingResult = MutableLiveData<ImageLoadingResult>()
    val imageLoadingResult: LiveData<ImageLoadingResult>
        get() = _imageLoadingResult

    private val _userLoadingResult = MutableLiveData<UserLoadingResult>()
    val userLoadingResult: LiveData<UserLoadingResult>
        get() = _userLoadingResult

    private val _showErrorMessage = MutableLiveData<Event<Int>>()
    val showErrorMessage: LiveData<Event<Int>>
        get() = _showErrorMessage

    private val _showSuccessMessage = MutableLiveData<Event<Int>>()
    val showSuccessMessage: LiveData<Event<Int>>
        get() = _showSuccessMessage

    private val _showLogoutFailureMessage = MutableLiveData<Event<Int>>()
    val showLogoutFailureMessage: LiveData<Event<Int>>
        get() = _showLogoutFailureMessage

    private val _showLogoutSuccess = MutableLiveData<Event<Unit>>()
    val showLogoutSuccess: LiveData<Event<Unit>>
        get() = _showLogoutSuccess

    private val _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged: LiveData<Boolean>
        get() = _isUserLogged

    init {
        _loggedUserName.value = loggedInUserRepository.getLoggedUser().displayName

    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

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
                    .subscribeToRequest(onNext = {
                        _imageLoadingResult.value =
                            ImageLoadingResult(filePath, isLoading = false)
                        _showSuccessMessage.value = Event(R.string.image_upload_successful)
                    }, onError = { error ->
                        if (error is HttpException) {
                            _showErrorMessage.value = when (error.code()) {
                                REQUEST_TOO_LARGE.toInt() -> Event(R.string.image_upload_error_413)
                                else -> Event(R.string.image_upload_error)
                            }
                            _imageLoadingResult.value = ImageLoadingResult(
                                error = _showErrorMessage.value?.peekContent(), isLoading = false
                            )
                        } else {
                            _showErrorMessage.value = Event(R.string.image_upload_error)
                            _imageLoadingResult.value = ImageLoadingResult(
                                error = R.string.image_upload_error,
                                isLoading = false
                            )
                        }
                    })
            )
        }
    }

    /**
     * Makes a server request to logout user. If request is successful, navigates user to login screen
     *
     */
    fun logout() {
        disposables.add(
            profileRepository.logout()
                .subscribeToRequest(onNext = {
                    authorizationFlagRepository.logoutUser()
                    _showLogoutSuccess.value = Event(Unit)
                }, onError = {
                    _showLogoutFailureMessage.value = Event(R.string.logout_error)
                })
        )
    }

    /**
     * Returns information about logged user
     *
     */
    fun getUserInfo() {
        _userLoadingResult.value = UserLoadingResult(isLoading = true)
        disposables.add(
            profileRepository.getUser()
                .subscribeToRequest(onNext = {
                    _userLoadingResult.value = UserLoadingResult(it, isLoading = false)
                }, onError = {
                    _userLoadingResult.value = UserLoadingResult(isLoading = false)
                })
        )
    }
}
