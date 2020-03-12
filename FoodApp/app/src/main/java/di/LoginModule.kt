package di

import dagger.Module
import dagger.Provides
import interactor.LoggedInUserStorage
import interactor.repositories.LoggedInUserRepository

/**
 * Provides [LoggedInUserRepository] for application
 *
 */
@Module
class LoginModule {

    @Provides
    @PerApplication
    fun provideStorage(): LoggedInUserStorage = LoggedInUserStorage()

    @Provides
    @PerApplication
    fun provideRepository(storageIn: LoggedInUserStorage): LoggedInUserRepository =
        LoggedInUserRepository(storageIn)


}