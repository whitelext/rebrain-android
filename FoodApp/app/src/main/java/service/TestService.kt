package service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.whitelext.foodapp.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import screen.main.MainActivity
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * An [IntentService] subclass
 *
 */
class TestService : Service() {

    private val CHANNEL_ID = "ForegroundService"

    private val disposables = CompositeDisposable()

    private fun handleActionTest() {
        disposables.add(Observable.interval(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.tag("TestService").i("I am alive!")
            })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleActionTest()
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        /**
         * Starts this service to perform action Test
         *
         */
        @JvmStatic
        fun startActionTest(context: Context) {
            val intent = Intent(context, TestService::class.java)
            startForegroundService(context, intent)
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
