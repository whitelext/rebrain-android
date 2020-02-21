package di

import android.content.Context
import android.content.SharedPreferences
import com.example.foodapp.FoodApplication
import dagger.Component
import di.network.OkHttpModule
import di.network.api.ProductsApiModule
import di.network.RetrofitModule
import interactor.AuthorizationFlagStorage
import interactor.FavoriteListStorage
import interactor.IntroFlagStorage
import interactor.ProductModeStorage
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.FavoritesRepository
import interactor.repositories.IntroFlagRepository
import interactor.repositories.ProductModeRepository
import network.auth.AuthApi
import network.products.ProductsApi
import network.user.UserApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import utils.SharedPreferencesHelper

/**
 * Application component
 *
 */
@Component(
    modules = [AppModule::class,
        SharedPreferencesModule::class,
        AuthorizationFlagModule::class,
        IntroFlagModule::class,
        ProductModeModule::class,
        FavoriteModule::class,
        RetrofitModule::class,
        OkHttpModule::class]
)
@PerApplication
interface AppComponent {
    fun appContext(): Context
    fun sharedPreferences(): SharedPreferences
    fun sharedPreferencesHelper(): SharedPreferencesHelper
    fun productModeStorage(): ProductModeStorage
    fun favoriteListRepository(): FavoritesRepository
    fun okHttpClient(): OkHttpClient
    fun retrofit(): Retrofit
    fun favoriteListStorage(): FavoriteListStorage
    fun authorizationFlagStorage(): AuthorizationFlagStorage
    fun introFlagStorage(): IntroFlagStorage
    fun authorizationFlagRepository(): AuthorizationFlagRepository
    fun productModeRepository(): ProductModeRepository
    fun introFlagRepository(): IntroFlagRepository
    fun inject(app: FoodApplication)
}