package di.network

import dagger.Module
import dagger.Provides
import di.PerApplication
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
    fun provideRetrofit(client: OkHttpClient, @Named("baseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder().client(client).baseUrl(baseUrl).build()
    }

    @Provides
    @Named("baseUrl")
    @PerApplication
    fun provideBaseUrl(): String = "http://api.android.srwx.net/api/v2/"


}