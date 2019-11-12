package di

import android.content.Context
import com.example.foodapp.FoodApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app : FoodApplication) {
    @Provides
    fun provideContext(): Context = app
}