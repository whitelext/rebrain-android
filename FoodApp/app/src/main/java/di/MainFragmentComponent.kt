package di

import dagger.Component
import di.network.api.ProductsApiModule
import screen.main.MainFragment
import screen.main.viewmodel.ProductListViewModel

/**
 * Component for [MainFragment]
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [ProductModule::class,
        ProductsApiModule::class]
)
interface MainFragmentComponent {
    fun productListViewModel(): ProductListViewModel
    fun inject(mainFragment: MainFragment)
}