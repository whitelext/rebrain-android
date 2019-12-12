package di

import android.content.Context
import android.content.SharedPreferences
import com.example.foodapp.FoodApplication
import dagger.Component
import interactor.AuthorizationFlagStorage
import interactor.IntroFlagStorage
import interactor.ProductModeStorage
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.IntroFlagRepository
import interactor.repositories.ProductModeRepository
import utils.SharedPreferencesHelper

@Component(
    modules = [AppModule::class,
        SharedPreferencesModule::class,
        AuthorizationFlagModule::class,
        IntroFlagModule::class,
        ProductModeModule::class]
)
@PerApplication
interface AppComponent {
    fun appContext(): Context
    fun sharedPreferences(): SharedPreferences
    fun sharedPreferencesHelper(): SharedPreferencesHelper
    fun productModeStorage(): ProductModeStorage
    fun authorizationFlagStorage(): AuthorizationFlagStorage
    fun introFlagStorage(): IntroFlagStorage
    fun authorizationFlagRepository(): AuthorizationFlagRepository
    fun productModeRepository(): ProductModeRepository
    fun introFlagRepository(): IntroFlagRepository
    fun inject(app: FoodApplication)
}