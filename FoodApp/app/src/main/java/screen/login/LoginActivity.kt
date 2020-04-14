package screen.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerLoginComponent
import di.LoginActivityModule
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import screen.main.MainActivity
import utils.BaseActivity
import javax.inject.Inject

/**
 * Login screen
 *
 */
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    lateinit var passwordEditDisposable: Disposable
    lateinit var usernameEditDisposable: Disposable
    lateinit var loginClickDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component =
            DaggerLoginComponent.builder()
                .appComponent(((this.application) as FoodApplication).getAppComponent())
                .loginActivityModule(LoginActivityModule(this))
                .build()
        component.inject(this)

        setContentView(R.layout.activity_login)


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer { loginState ->

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            loginState.usernameError?.let { error ->
                username.error = getString(error)
            }
            loginState.passwordError?.let { error ->
                password.error = getString(error)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer { loginResult ->

            if (loginResult.isLoading) {
                return@Observer
            }

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)
        })

        loginClickDisposable = login.clicks().subscribe {
            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        usernameEditDisposable = username.textChanges().subscribe {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        passwordEditDisposable = password.textChanges().subscribe {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }
    }

    override fun onPause() {
        loginClickDisposable.dispose()
        usernameEditDisposable.dispose()
        passwordEditDisposable.dispose()
        super.onPause()
    }

    private fun updateUiWithUser(model: LoggedInUser) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        MainActivity.start(this)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun start(context: Context) {
            startActivity(context, Intent(context, LoginActivity::class.java), null)
        }
    }
}


