package di

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import interactor.AuthorizationFlagStorage
import interactor.IntroFlagStorage
import interactor.ProductModeStorage
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AutrorizationFlagStorageModule::class,
        IntroFlagStorageModule::class,
        ProductModeStorageModule::class,
        SharedPreferencesModule::class,
        AppModule::class]
)
interface AppComponent {
    fun productModeStorage(): ProductModeStorage
    fun authorizationFlagStorage(): AuthorizationFlagStorage
    fun introFlagStorage(): IntroFlagStorage
    fun sharedPreferences(): SharedPreferences
    fun appContext(): Context
}