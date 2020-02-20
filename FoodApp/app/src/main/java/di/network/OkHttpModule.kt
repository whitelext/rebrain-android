package di.network

import dagger.Module
import dagger.Provides
import di.PerApplication
import okhttp3.OkHttpClient
import utils.LogInterceptor

/**
 * Provides [OkHttpClient] for application
 *
 */
@Module
class OkHttpModule {

    @Provides
    @PerApplication
    fun provideOkHttpClient() = OkHttpClient().newBuilder().addInterceptor(LogInterceptor()).build()

}