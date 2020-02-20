package di.network.api

import dagger.Module
import dagger.Provides
import di.PerApplication
import di.PerScreen
import network.products.ProductsApi
import retrofit2.Retrofit

/**
 * Provides [ProductsApi]
 *
 */
@Module
class ProductsApiModule {
    @Provides
    @PerScreen
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

}