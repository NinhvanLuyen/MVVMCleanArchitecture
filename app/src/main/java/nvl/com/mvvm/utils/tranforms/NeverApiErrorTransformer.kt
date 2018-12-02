package nvl.com.mvvm.utils.tranforms

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import nvl.com.mvvm.data.remote.api.ApiError

class NeverApiErrorTransformer<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.doOnError { t ->
            run {
                errorAction?.invoke(ApiError.fromThrowable(t))
            }
        }.onErrorResumeNext { _: Throwable -> Observable.empty() }
    }

    val errorAction: ((ApiError) -> Any)?

    constructor() {
        errorAction = null
    }

    constructor(errorAction: (ApiError) -> Any) {
        this.errorAction = errorAction
    }
}