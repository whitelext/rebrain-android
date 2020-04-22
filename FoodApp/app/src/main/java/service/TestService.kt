package service

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * An [IntentService] subclass
 *
 */
class TestService : Service() {

    private val disposables = CompositeDisposable()

    private fun handleActionTest() {
        disposables.add(Observable.interval(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.tag("TestService").i("I am alive!")
            })
    }

    override fun onCreate() {
        handleActionTest()
        super.onCreate()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
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
