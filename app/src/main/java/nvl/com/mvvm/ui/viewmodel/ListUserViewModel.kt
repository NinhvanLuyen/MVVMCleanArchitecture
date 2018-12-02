package nvl.com.mvvm.ui.viewmodel

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import nvl.com.mvvm.data.remote.base_model.LoadMoreData
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.domain.UserUseCase
import nvl.com.mvvm.utils.tranforms.Transformers

interface ListUserViewModel {
    class Data {
        var showOnlyBookmarked = ObservableField<Boolean>(false)
        var showLoading = ObservableField<Boolean>(false)
        var noData = ObservableField<Boolean>(false)
        var isRefresh = ObservableField<Boolean>(false)
        var loadDataError = ObservableField<Boolean>(false)
        var errorMessage = ObservableField<String>("")
    }

    interface OutPut {
        fun renderListUser(): Observable<Pair<MutableList<User>, Boolean>>
        fun renderListUserBookmarked(): Observable<MutableList<User>>
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


    class ViewModel(userUseCase: UserUseCase) : BaseViewModel(userUseCase), OutPut, Input, Error {
        var data = Data()
        var input = this
        var output = this
        var error = this
        private var lsTerm = mutableListOf<User>()
        private var listDataUserBookmarkedTerm = mutableListOf<User>()
        private var renderData = BehaviorSubject.create<Pair<MutableList<User>, Boolean>>()
        private var renderDataUserBookmarked = BehaviorSubject.create<MutableList<User>>()
        private var nextPage = PublishSubject.create<Boolean>()
        private var addItemErrorBottomList = PublishSubject.create<Boolean>()
        private var callApi = PublishSubject.create<Int>()
        private var currentPage = 1
        private var canLoadmore = false
        override fun renderListUser(): Observable<Pair<MutableList<User>, Boolean>> = renderData
        override fun renderListUserBookmarked(): Observable<MutableList<User>> =renderDataUserBookmarked


        private lateinit var loadData: Observable<LoadMoreData<User>>

        init {
            data.showLoading.set(true)
            arguments.subscribe {
                if (lsTerm.isEmpty()) {
                    callApi.onNext(currentPage)
                    nextPage.onNext(true)
                } else {
                    renderData.onNext(Pair(lsTerm, canLoadmore))
                }
                if (listDataUserBookmarkedTerm.isNotEmpty())
                {
                    renderDataUserBookmarked.onNext(listDataUserBookmarkedTerm)
                }
            }!!
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
            disposables.add(userUseCase.getListBookMarkUser()
                    .compose(Transformers.pipeApiErrorTo(apiError))
                    .compose(bindTolifecycle())
                    .subscribe {
                        data.noData.set(it.isEmpty())
                        renderDataUserBookmarked.onNext(it)
                        listDataUserBookmarkedTerm.addAll(it)
                    })
            disposables.add(loadData.subscribe())


        }

        fun getListLiveData() = userUseCase.getLiveDataUser()

        fun call(nextPage: Int): Observable<LoadMoreData<User>> = userUseCase.getListUser(nextPage)

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

