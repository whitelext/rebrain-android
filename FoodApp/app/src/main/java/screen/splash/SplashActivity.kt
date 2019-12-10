package screen.splash

import android.content.Context
import android.os.Bundle
import com.example.foodapp.FoodApplication
import com.example.foodapp.R
import di.DaggerIntroComponent
import di.IntroModule
import kotlinx.coroutines.*
import screen.intro.IntroActivity
import screen.intro.viewmodel.IntroViewModel
import screen.main.MainActivity
import utils.BaseActivity
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * The Splash which is displayed before the launch of MainActivity
 */
class SplashActivity : BaseActivity(), CoroutineScope {
    @Inject
    lateinit var viewModel: IntroViewModel
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component =
            DaggerIntroComponent.builder()
                .appComponent(((this.application) as FoodApplication).getAppComponent())
                .introModule(IntroModule(this))
                .build()
        component.inject(this)
        setContentView(R.layout.activity_splash)
        navigateToActivityWithDelay(this, 500)
    }

    private fun navigateToActivityWithDelay(context: Context, delayTime: Long) = launch {
        delay(delayTime)
        if (viewModel.isIntroShowed()) {
            MainActivity.start(context)
        } else {
            IntroActivity.start(context)
            viewModel.showIntro()
        }
    }

}

