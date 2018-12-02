package nvl.com.mvvm.data.remote.api

import io.reactivex.Single
import nvl.com.mvvm.data.remote.base_model.LoadMoreData
import nvl.com.mvvm.data.entities.User

interface ApiService {
    fun getListUser(page: Int): Single<LoadMoreData<User>>

}