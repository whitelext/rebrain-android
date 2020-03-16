package screen.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerLoginComponent
import di.LoginActivityModule
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

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
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

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


