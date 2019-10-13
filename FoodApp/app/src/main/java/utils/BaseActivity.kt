package utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

/**
 *  Root Activity class that helps to observe activity lifecycle avoiding boilerplate code for multiple activities
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(Logger(localClassName))
    }

    override fun onRestart() {
        super.onRestart()
        Timber.tag("lifecycle").i("onRestart was called from $localClassName")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.tag("lifecycle").i("onSaveInstanceState was called from $localClassName")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.tag("lifecycle").i("onRestoreInstanceState was called from $localClassName")
    }
}
