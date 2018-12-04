package nvl.com.mvvm.ui.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import nvl.com.mvvm.data.remote.api.ApiError
import nvl.com.mvvm.domain.UserUseCase

open class BaseViewModel(val userUseCase: UserUseCase) : ViewModel(), LifecycleObserver {
    val disposables = CompositeDisposable()
    protected var apiError = BehaviorSubject.create<ApiError>()
    protected val arguments = BehaviorSubject.create<Bundle>()

    fun addObserver(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    fun removeObserver(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    fun <T> bindTolifecycle(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun setArgument(bundle: Bundle?) {
        arguments.onNext(bundle ?: Bundle())
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}