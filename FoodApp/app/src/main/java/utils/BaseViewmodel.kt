package utils

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    /**
     * Subscribing to server request
     *
     * @param T is domain layer class
     * @param onNext lambda that is invoked when request is successful
     * @param onError lambda that is invoked when there is some error
     */
    protected fun <T : Any> Single<T>.subscribeToRequest(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNext, onError)
}