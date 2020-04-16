package screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitelext.foodapp.R
import domain.User
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.AuthorizationTokenRepository
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * [ViewModel] for login screen
 *
 */
class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val authorizationTokenRepository: AuthorizationTokenRepository,
    private val authorizationFlagRepository: AuthorizationFlagRepository,
    private val loggedInUserRepository: LoggedInUserRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    /**
     * Makes a server request for login in viewModel scope
     * if login is successful saves authorization token to [authorizationTokenRepository] i
     *
     */
    fun login(username: String, password: String) {
        disposables.add(
            loginRepository.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val user: User = response.convertToKotlinClass()
                    authorizationTokenRepository.saveAuthorizationToken(user.accesToken)
                    authorizationFlagRepository.loginUser()
                    val loggedUser = LoggedInUser(user.name)
                    loggedInUserRepository.setLoggedUser(loggedUser)
                    _loginResult.value = LoginResult(
                        success = loggedUser,
                        isLoading = false
                    )
                },
                    {
                        _loginResult.value =
                            LoginResult(error = R.string.login_failed, isLoading = false)
                    }
                )
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
