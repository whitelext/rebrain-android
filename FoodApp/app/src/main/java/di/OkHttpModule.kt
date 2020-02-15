package di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * Provides [OkHttpClient] for application
  *
 */
@Module
class OkHttpModule {

    @Provides
    @PerApplication
    fun provideOkHttpClient() = OkHttpClient()

}