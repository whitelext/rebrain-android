package workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class MainWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        Timber.tag("WorkManagerTest").i("Since 5 minutes")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
    }
}