package di

import dagger.Component
import interactor.repositories.ProductsRepository
import okhttp3.OkHttpClient
import screen.main.MainFragment
import screen.main.viewmodel.ProductListViewModel
import screen.main.viewmodel.ProductListViewModelFactory

/**
 * Component for [MainFragment]
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [ProductModule::class]
)
interface MainFragmentComponent {
    fun productsRepository(): ProductsRepository
    fun productListViewModelFactory(): ProductListViewModelFactory
    fun okHttpClient(): OkHttpClient
    fun productListViewModel(): ProductListViewModel
    fun inject(mainFragment: MainFragment)
}