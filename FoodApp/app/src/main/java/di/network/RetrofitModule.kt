package di.network

import dagger.Module
import dagger.Provides
import di.PerApplication
import di.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Provides [Retrofit] for application
 *
 */
@Module
class RetrofitModule {

    @Provides
    @PerApplication
    fun provideRetrofit(client: OkHttpClient, @Named(Constants.BASE_URL_NAMED) baseUrl: String): Retrofit {
        return Retrofit.Builder().client(client).baseUrl(baseUrl).build()
    }

    @Provides
    @Named(Constants.BASE_URL_NAMED)
    @PerApplication
    fun provideBaseUrl(): String = "http://api.android.srwx.net/api/v2/"


}