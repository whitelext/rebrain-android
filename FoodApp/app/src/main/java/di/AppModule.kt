package di

import android.content.Context
import com.example.foodapp.FoodApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(app: FoodApplication): Context = app
}