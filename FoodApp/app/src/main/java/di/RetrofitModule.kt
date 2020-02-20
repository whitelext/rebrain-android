package di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Provides [Retrofit] for application
 *
 */
@Module
class RetrofitModule {

    @Provides
    @PerApplication
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(client).baseUrl("http://api.android.srwx.net/api/v2/").build()
    }

}