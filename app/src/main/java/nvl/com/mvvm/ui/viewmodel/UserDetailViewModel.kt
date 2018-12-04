package nvl.com.mvvm.ui.viewmodel

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nvl.com.mvvm.data.entities.Reputation
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.data.remote.base_model.LoadMoreData
import nvl.com.mvvm.domain.UserUseCase
import nvl.com.mvvm.utils.Utils
import nvl.com.mvvm.utils.tranforms.Transformers

interface UserDetailViewModel {

    interface OutPut {
        fun renderReputations(): Observable<Pair<MutableList<Reputation>, Boolean>>
    }

    interface Input {
        fun nextPage()
        fun swipeRefresh()
        fun bookMarkUser(user: User, isBookmark: Boolean)
        fun retryOnErrorItem()

    }

    interface Error {
        //        fun showItemError(): Observable<String>
        fun addItemErrorBottomList(): Observable<Boolean>
    }

    class Data {
        var avatar = ObservableField<String>()
        var name = ObservableField<String>()
        var reputation = ObservableField<String>()
        var location = ObservableField<String>()
        var errorMessage = ObservableField<String>()
        var showLoading = ObservableField<Boolean>(false)
        var noData = ObservableField<Boolean>(false)
        var isRefresh = ObservableField<Boolean>(false)
        var loadDataError = ObservableField<Boolean>(false)
        var isBoomarked = ObservableField<Boolean>()
    }

    class ViewModel(userUseCase: UserUseCase) : BaseViewModel(userUseCase), OutPut, Input, Error {
        private lateinit var user: User
        var data = Data()
        var input = this
        var output = this
        var error = this
        private var lsTerm = mutableListOf<Reputation>()
        private var renderData = PublishSubject.create<Pair<MutableList<Reputation>, Boolean>>()
        private var nextPage = PublishSubject.create<Boolean>()
        private var addItemErrorBottomList = PublishSubject.create<Boolean>()
        private var callApi = PublishSubject.create<Int>()
        private var currentPage = 1
        private var canLoadmore = false
        override fun renderReputations(): Observable<Pair<MutableList<Reputation>, Boolean>> = renderData


        private lateinit var loadData: Observable<LoadMoreData<Reputation>>


        init {
            data.showLoading.set(true)
            disposables.add(arguments.compose(bindTolifecycle())
                    .subscribe {
                        user = it.getParcelable("user") ?: User()
                        data.name.set(user.display_name)
                        data.avatar.set(user.profile_image)
                        data.isBoomarked.set(user.isBookmark)
                        data.location.set(user.location)
                        data.reputation.set(Utils.getShortNumberFormat(user.reputation))
                        if (lsTerm.isEmpty()) {

                            callApi.onNext(currentPage)
                            nextPage.onNext(true)

                        } else {
                            renderData.onNext(Pair(lsTerm, canLoadmore))
                        }
                    }!!)
            disposables.add(apiError
                    .compose(bindTolifecycle())
                    .subscribe {
                        data.isRefresh.set(false)
                        data.showLoading.set(false)
                        if (lsTerm.size > 0) {
                            //add item_error at the end recyclerview
                            addItemErrorBottomList.onNext(true)
                        } else {
                            data.loadDataError.set(true)
                            data.errorMessage.set(it.errorMessage)
                            ""
                        }
                    }
            )
            loadData = callApi.compose<Int>(Transformers.takeWhen(nextPage))
                    .switchMap(this::call)
                    .doOnError { loadData.subscribe() }
                    .compose(Transformers.pipeApiErrorTo(apiError))
                    .compose(bindTolifecycle())
                    .doOnNext {
                        data.loadDataError.set(false)
                        data.showLoading.set(false)
                        data.isRefresh.set(false)
                        if (it.getHasMore())
                            currentPage++
                        callApi.onNext(currentPage)
                        canLoadmore = (currentPage < 1000)
                        lsTerm.addAll(it.getDatas()!!.toMutableList())
                        renderData.onNext(Pair(it.getDatas()!!.toMutableList(), canLoadmore))
                    }
            disposables.add(loadData.subscribe())

        }

        fun call(nextPage: Int): Observable<LoadMoreData<Reputation>> = userUseCase.getListReputation(nextPage,"${user.user_id}")

        override fun bookMarkUser(user: User, isBookmark: Boolean) {
            if (isBookmark)
                userUseCase.bookMarkUser(user)
                        .subscribe {
                        }
            else
                userUseCase.unBookMarkUser(user)
                        .subscribe {
                        }


        }

        override fun swipeRefresh() {
            data.loadDataError.set(false)
            data.showLoading.set(true)
            data.isRefresh.set(true)
            lsTerm = mutableListOf()
            currentPage = 1
            callApi.onNext(currentPage)
            nextPage.onNext(true)

        }

        override fun nextPage() {
            nextPage.onNext(true)
        }

        override fun retryOnErrorItem() {
            callApi.onNext(currentPage)
            nextPage.onNext(true)
        }

        override fun addItemErrorBottomList(): Observable<Boolean> = addItemErrorBottomList

    }
}