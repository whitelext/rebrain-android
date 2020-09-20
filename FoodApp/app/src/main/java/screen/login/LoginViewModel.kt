package screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitelext.foodapp.R
import interactor.repositories.LoginRepository
import io.reactivex.disposables.CompositeDisposable
import utils.BaseViewModel

/**
 * [ViewModel] for login screen
 *
 */
class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val disposables = CompositeDisposable()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    /**
     * Makes a server request for login in viewModel scope
     *
     */
    fun login(username: String, password: String) {
        disposables.add(
            loginRepository.login(username, password)
                .subscribeToRequest(onNext = { user ->
                    _loginResult.value = LoginResult(
                        success = LoggedInUser(displayName = user.name,displayAvatar = user.avatar),
                        isLoading = false
                    )
                }, onError = {
                    _loginResult.value =
                        LoginResult(error = R.string.login_failed, isLoading = false)
                })
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return true
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.matches(Regex("[^-\\s]+")) && password.length < 9
    }
}
