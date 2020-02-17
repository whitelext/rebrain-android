package utils

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * Okhttp Interceptor that logs the outgoing request and the incoming response
 *
 */
class LogInterceptor : Interceptor {
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