package nvl.com.mvvm.data.remote.api

import io.reactivex.Single
import nvl.com.mvvm.data.remote.base_model.LoadMoreData
import nvl.com.mvvm.data.entities.User
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface HttpRepository
{

    @GET("users")
    fun getListUsers(@QueryMap option: Map<String, String>): Single<LoadMoreData<User>>

}