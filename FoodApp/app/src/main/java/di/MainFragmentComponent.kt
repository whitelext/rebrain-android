package di

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import interactor.AuthorizationFlagStorage
import interactor.IntroFlagStorage
import interactor.ProductModeStorage
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.IntroFlagRepository
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository
import screen.main.MainFragment
import screen.main.viewmodel.ProductListViewModel
import screen.main.viewmodel.ProductListViewModelFactory
import utils.SharedPreferencesHelper

@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [AuthorizationFlagModule::class,
        IntroFlagModule::class,
        ProductModeModule::class,
        SharedPreferencesModule::class,
        ProductModule::class]
)
interface MainFragmentComponent {
    fun productModeStorage(): ProductModeStorage
    fun authorizationFlagStorage(): AuthorizationFlagStorage
    fun introFlagStorage(): IntroFlagStorage
    fun sharedPreferences(): SharedPreferences
    fun appContext(): Context
    fun sharedPreferencesHelper(): SharedPreferencesHelper
    fun authorizationFlagRepository(): AuthorizationFlagRepository
    fun productModeRepository(): ProductModeRepository
    fun introFlagRepository(): IntroFlagRepository
    fun productsRepository(): ProductsRepository
    fun productListViewModelFactory(): ProductListViewModelFactory
    fun productListViewModel(): ProductListViewModel
    fun inject(mainFragment: MainFragment)
}