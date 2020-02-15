package di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import timber.log.Timber

/**
 * Provides [OkHttpClient] for application
 *
 */
@Module
class OkHttpModule {

    @Provides
    @PerApplication
    fun provideOkHttpClient() = OkHttpClient().newBuilder().addInterceptor(
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val t1 = System.nanoTime()
                Timber.tag("Network").i(
                    "Sending request %s on %s%n%s",
                    request.url, chain.connection(), request.headers
                )
                val response = chain.proceed(request)
                val t2 = System.nanoTime()
                Timber.tag("Network").i(
                    "Received response for %s in %.1fms%n%s",
                    response.request.url, (t2 - t1) / 1e6, response.headers
                )
                return response
            }

        }
    ).build()

}