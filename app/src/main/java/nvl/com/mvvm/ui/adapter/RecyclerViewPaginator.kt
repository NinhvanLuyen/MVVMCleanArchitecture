package nvl.com.mvvm.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.Disposable

class RecyclerViewPaginator(private val recyclerView: RecyclerView, private val nextPage: () -> Any) {

    private var subscription: Disposable? = null

    init {
        start()
    }

    fun start() {
        stop()

        subscription = RxRecyclerView.scrollEvents(recyclerView)
                .map { _ -> recyclerView.layoutManager!! }
                .ofType(LinearLayoutManager::class.java)
                .map { t -> displayedItemFromLinearLayout(t) }
                .filter { t -> t.second != 0 }
                .filter { t -> visibleItemIsCloseToBottom(t) }
                .toFlowable(BackpressureStrategy.DROP)
                .distinctUntilChanged()
                .subscribe { _: Pair<Int, Int> -> nextPage() }
    }

    fun stop() {
        subscription?.dispose()
        subscription = null
    }

    private fun displayedItemFromLinearLayout(linearLayoutManager: LinearLayoutManager): Pair<Int, Int> {
        return Pair(linearLayoutManager.findLastVisibleItemPosition(), linearLayoutManager.itemCount)
    }

    private fun visibleItemIsCloseToBottom(visibleItemOfTotal: Pair<Int, Int>): Boolean {
        return visibleItemOfTotal.first == visibleItemOfTotal.second - 1
    }
}