package screen.splash

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.foodapp.MainActivity
import com.example.foodapp.R
import kotlinx.coroutines.*
import screen.intro.IntroActivity
import kotlin.coroutines.CoroutineContext


/**
 * The Splash which is displayed before the launch of MainActivity
 */
class SplashActivity : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigateToActivityWithDelay(this,500)
    }

    private fun navigateToActivityWithDelay(context:Context,delayTime : Long) = launch {
        delay(delayTime)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@SplashActivity)
        if(sharedPref.getBoolean("is_intro_needed",true)){
            sharedPref.edit().putBoolean("is_intro_needed",false).apply()
            IntroActivity.start(context)
        }
        else{
            MainActivity.start(context)
        }
    }


}

