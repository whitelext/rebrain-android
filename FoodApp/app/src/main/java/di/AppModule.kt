package di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Provides context of application
 *
 */
@Module
class AppModule(val app: Application) {
    @Provides
    @PerApplication
    fun provideContext(): Context = app
}