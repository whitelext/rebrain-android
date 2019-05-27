package screen.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.MainActivity
import com.example.foodapp.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// The Splash which is displayed before the launch of MainActivity

class SplashActivity : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this,MainActivity::class.java)
        navigateToActivityWithDelay(intent,500)
    }

    private fun navigateToActivityWithDelay(intent : Intent,delayTime : Long) = launch {
        delay(delayTime)
        startActivity(intent)
    }
}

