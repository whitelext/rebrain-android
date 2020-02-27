package di.network.api

import dagger.Module
import dagger.Provides
import di.PerScreen
import network.auth.AuthApi
import retrofit2.Retrofit

/**
 * Provides [AuthApi]
 *
 */
@Module
class AuthApiModule {
    @Provides
    @PerScreen
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }


}