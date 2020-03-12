package di

import android.content.Context
import android.content.SharedPreferences
import com.whitelext.foodapp.FoodApplication
import dagger.Component
import di.network.OkHttpModule
import di.network.RetrofitModule
import interactor.*
import interactor.repositories.*
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
        AuthorizationTokenModule::class,
        LoginModule::class,
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
    fun authorizationTokenStorage(): AuthorizationTokenStorage
    fun authorizationTokenRepository(): AuthorizationTokenRepository
    fun okHttpClient(): OkHttpClient
    fun retrofit(): Retrofit
    fun loggedInUserStorage(): LoggedInUserStorage
    fun loggedInUserRepository(): LoggedInUserRepository
    fun favoriteListStorage(): FavoriteListStorage
    fun authorizationFlagStorage(): AuthorizationFlagStorage
    fun introFlagStorage(): IntroFlagStorage
    fun authorizationFlagRepository(): AuthorizationFlagRepository
    fun productModeRepository(): ProductModeRepository
    fun introFlagRepository(): IntroFlagRepository
    fun inject(app: FoodApplication)
}