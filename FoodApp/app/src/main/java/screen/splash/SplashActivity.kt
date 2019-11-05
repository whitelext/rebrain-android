package screen.splash

import android.content.Context
import android.os.Bundle
import com.example.foodapp.R
import interactor.IntroFlagStorage
import interactor.repositories.IntroFlagRepository
import kotlinx.coroutines.*
import screen.intro.IntroActivity
import screen.main.MainActivity
import utils.BaseActivity
import utils.SharedPreferencesHelper
import kotlin.coroutines.CoroutineContext

/**
 * The Splash which is displayed before the launch of MainActivity
 */
class SplashActivity : BaseActivity(), CoroutineScope {
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
        val introRepository =
            IntroFlagRepository(IntroFlagStorage(SharedPreferencesHelper(this@SplashActivity)))
        if (introRepository.isIntroShowed()) {
            MainActivity.start(context)
        } else {
            IntroActivity.start(context)
            introRepository.showIntro()
        }
    }

}

