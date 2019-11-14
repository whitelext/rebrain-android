package di

import android.content.Context
import com.example.foodapp.FoodApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun appContext(): Context
    fun inject(app: FoodApplication)
}