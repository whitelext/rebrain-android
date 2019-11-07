package screen.splash

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.foodapp.R
import interactor.IntroFlagStorage
import interactor.repositories.IntroFlagRepository
import kotlinx.coroutines.*
import screen.intro.IntroActivity
import screen.intro.viewmodel.IntroViewModel
import screen.intro.viewmodel.IntroViewModelFactory
import screen.main.MainActivity
import utils.BaseActivity
import utils.SharedPreferencesHelper
import kotlin.coroutines.CoroutineContext

/**
 * The Splash which is displayed before the launch of MainActivity
 */
class SplashActivity : BaseActivity(), CoroutineScope {
    private lateinit var viewmodel: IntroViewModel
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigateToActivityWithDelay(this, 500)
    }

    private fun navigateToActivityWithDelay(context: Context, delayTime: Long) = launch {
        delay(delayTime)
        viewmodel = ViewModelProviders.of(
            this@SplashActivity,
            IntroViewModelFactory(IntroFlagRepository(IntroFlagStorage(SharedPreferencesHelper(this@SplashActivity))))
        ).get(IntroViewModel::class.java)
        if (viewmodel.isIntroShowed()) {
            MainActivity.start(context)
        } else {
            IntroActivity.start(context)
            viewmodel.showIntro()
        }
    }

}

