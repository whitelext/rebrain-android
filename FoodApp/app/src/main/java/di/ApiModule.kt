package di

import dagger.Module
import dagger.Provides
import network.auth.AuthApi
import network.products.ProductsApi
import network.user.UserApi
import retrofit2.Retrofit

/**
 * Provides Api
 *
 */
@Module
class ApiModule {
    @Provides
    @PerApplication
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @PerApplication
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

    @Provides
    @PerApplication
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}