package nvl.com.mvvm.utils.tranforms

import io.reactivex.Observable
import io.reactivex.subjects.Subject
import nvl.com.mvvm.data.remote.api.ApiError

object Transformers {

    fun <S, T> takeWhen(observable: Observable<T>) = TakeWhenTransformer<S, T>(observable)
    fun <T> pipeApiErrorTo(errorSubject: Subject<ApiError>) = NeverApiErrorTransformer<T>(errorSubject::onNext)

}
