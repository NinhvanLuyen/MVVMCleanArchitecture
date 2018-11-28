package nvl.com.mvvm.services.api

import io.reactivex.Single
import nvl.com.mvvm.services.model.BaseResponse
import nvl.com.mvvm.services.model.LoadMoreData
import nvl.com.mvvm.services.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface HttpRepository
{

    @GET("users")
    fun getListUsers(@QueryMap option: Map<String, String>): Single<LoadMoreData<User>>

}