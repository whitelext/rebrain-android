package di

import android.content.Context
import com.example.foodapp.FoodApplication
import dagger.Component

@Component(
    modules = [AppModule::class]
)
@PerApplication
interface AppComponent {
    fun appContext(): Context
    fun inject(app: FoodApplication)
}