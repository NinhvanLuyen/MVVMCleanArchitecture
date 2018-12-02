package nvl.com.mvvm.utils.tranforms

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class TakeWhenTransformer<S, T>(private val observable: Observable<T>) : ObservableTransformer<S, S> {

    override fun apply(upstream: Observable<S>): ObservableSource<S> {
        return observable.withLatestFrom(upstream, BiFunction { _, x -> x })
    }

}
