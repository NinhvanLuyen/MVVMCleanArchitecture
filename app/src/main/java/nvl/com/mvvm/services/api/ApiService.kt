package nvl.com.mvvm.services.api

import io.reactivex.Single
import nvl.com.mvvm.services.model.LoadMoreData
import nvl.com.mvvm.data.entities.User

interface ApiService {
    fun getListUser(page: Int): Single<LoadMoreData<User>>

}