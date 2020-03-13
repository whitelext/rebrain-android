package di

import android.content.Context
import com.whitelext.foodapp.FoodApplication
import dagger.Component
import di.network.OkHttpModule
import di.network.RetrofitModule
import interactor.repositories.FavoritesRepository
import interactor.repositories.LoggedInUserRepository
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
    fun loggedInUserRepository(): LoggedInUserRepository
    fun favoriteListRepository(): FavoritesRepository
    fun okHttpClient(): OkHttpClient
    fun retrofit(): Retrofit
    fun inject(app: FoodApplication)
}