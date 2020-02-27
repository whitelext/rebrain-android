package di.network.api

import dagger.Module
import dagger.Provides
import di.PerScreen
import network.user.UserApi
import retrofit2.Retrofit

/**
 * Provides [UserApi]
 *
 */
@Module
class UserApiModule {
    @Provides
    @PerScreen
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}