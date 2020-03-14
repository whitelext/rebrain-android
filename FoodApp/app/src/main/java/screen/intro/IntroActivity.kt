package screen.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerIntroComponent
import di.IntroModule
import interactor.repositories.AuthorizationFlagRepository
import kotlinx.android.synthetic.main.activity_intro.*
import screen.login.LoginActivity
import screen.main.MainActivity
import utils.BaseActivity
import javax.inject.Inject

/*
** Activity that shows new users what our app can do
*
* A flag that activity was shown and no more needed is stored in Shared Preferences
*
* User will be navigated to Main Screen after taping any part of screen
 */
class IntroActivity : BaseActivity() {

    @Inject
    lateinit var authorizationFlagRepository: AuthorizationFlagRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val component =
            DaggerIntroComponent.builder()
                .appComponent(((this.application) as FoodApplication).getAppComponent())
                .introModule(IntroModule(this))
                .build()
        component.inject(this)

        intro_screen.setOnClickListener {
            if (authorizationFlagRepository.isUserAuthorized()) {
                MainActivity.start(this)
            } else {
                LoginActivity.start(this)
            }
            finishAffinity()
        }
    }

    companion object {
        fun start(context: Context) {
            startActivity(context, Intent(context, IntroActivity::class.java), null)
        }
    }
}
