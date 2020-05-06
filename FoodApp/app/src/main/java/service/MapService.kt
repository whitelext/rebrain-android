package service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.whitelext.foodapp.R
import screen.main.MainActivity


private const val EXTRA_POINT_ADDRESS = "service.extra.POINT_ADDRESS"
private const val EXTRA_POINT_DISTANCE = "service.extra.POINT_DISTANCE"


/**
 *Service that shows notification with closest pickup point in [MapsActivity]
 *
 */
class MapService : Service() {

    private val CHANNEL_ID = "ForegroundMapService"


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val closestPointAddress = intent?.getStringExtra(EXTRA_POINT_ADDRESS)
        val closestPointDistance = intent?.getFloatExtra(EXTRA_POINT_DISTANCE, 0f)
        val closestPointFormattedInfo: String =
            getString(R.string.closestPickupPointInfo1) + "$closestPointAddress" + getString(R.string.closestPickupPointInfo2) + closestPointDistance

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(closestPointFormattedInfo)
            )
            .build()

        startForeground(1, notification)
        return super.onStartCommand(intent, flags, startId)
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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        /**
         * Starts service
         *
         * @param closestPickupPointInfo is  info about closest PickupPoint to current user location
         */
        @JvmStatic
        fun startMapService(context: Context, closestPickupPointInfo: Pair<String, Float>) {
            val intent = Intent(context, MapService::class.java).apply {
                putExtra(EXTRA_POINT_ADDRESS, closestPickupPointInfo.first)
                putExtra(EXTRA_POINT_DISTANCE, closestPickupPointInfo.second)
            }
            context.startService(intent)
        }

        /**
         * Stops service
         *
         */
        @JvmStatic
        fun stop(context: Context) {
            val intent = Intent(context, MapService::class.java)
            context.stopService(intent)
        }

    }
}
