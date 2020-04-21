package service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * An [IntentService] subclass
 *
 */
class TestService : IntentService("TestService") {

    private val disposables = CompositeDisposable()

    override fun onHandleIntent(intent: Intent?) {
        handleActionTest()
    }

    private fun handleActionTest() {
        Timber.tag("TestService").i("Service handle called")
        disposables.add(Observable.interval(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.tag("TestService").i("I am alive!")
            })
    }

    companion object {
        /**
         * Starts this service to perform action Test
         *
         */
        @JvmStatic
        fun startActionTest(context: Context) {
            val intent = Intent(context, TestService::class.java)
            context.startService(intent)
        }

        /**
         * Stops service
         *
         */
        @JvmStatic
        fun stopActionTest(context: Context) {
            val intent = Intent(context, TestService::class.java)
            context.stopService(intent)
        }

    }
}
