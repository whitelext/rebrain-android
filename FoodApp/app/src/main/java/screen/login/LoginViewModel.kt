package screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.AuthorizationTokenRepository
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.LoginRepository
import kotlinx.coroutines.launch
import utils.Result

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

        viewModelScope.launch {
            val response = loginRepository.login(username, password)
            _loginResult.value = when (response) {
                is Result.Success -> {
                    authorizationTokenRepository.saveAuthorizationToken(response.data.accesToken)
                    authorizationFlagRepository.loginUser()
                    loggedInUserRepository.setLoggedUser(
                        LoggedInUser(displayName = response.data.name)
                    )
                    LoginResult(
                        success = LoggedInUser(displayName = response.data.name),
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    LoginResult(error = R.string.login_failed, isLoading = false)
                }
            }
        }
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
