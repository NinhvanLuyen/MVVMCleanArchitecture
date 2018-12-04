package nvl.com.mvvm.data.remote.api

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import nvl.com.mvvm.data.entities.Reputation
import nvl.com.mvvm.utils.ErrorCodes
import nvl.com.mvvm.utils.ErrorMessage
import nvl.com.mvvm.utils.DeviceUtils
import nvl.com.mvvm.data.remote.base_model.LoadMoreData
import nvl.com.mvvm.data.entities.User

class ApiServiceImp(val httpRepository: HttpRepository) : ApiService
{
    override fun getListUser(page: Int): Single<LoadMoreData<User>> {
        return Single.defer {
            if (!DeviceUtils.isNetworkAvailable()) {
                Single.error(ApiException(ApiError(ErrorCodes.NET_WORK_PROBLEM, ErrorMessage.NET_WORK_PROBLEM)))
            } else {
                val option = HashMap<String, String>()
                option.put("page", "$page")
                option.put("pagesize", "30")
                option.put("site", "stackoverflow")
                httpRepository.getListUsers(option)
                        .compose(ApiTransformer())
                        .subscribeOn(Schedulers.io())
            }
        }
    }
    override fun getReputation(page: Int,userID:String): Single<LoadMoreData<Reputation>> {
        return Single.defer {
            if (!DeviceUtils.isNetworkAvailable()) {
                Single.error(ApiException(ApiError(ErrorCodes.NET_WORK_PROBLEM, ErrorMessage.NET_WORK_PROBLEM)))
            } else {
                val option = HashMap<String, String>()
                option.put("page", "$page")
                option.put("pagesize", "30")
                option.put("site", "stackoverflow")
                httpRepository.getListReputation(userID,option)
                        .compose(ApiTransformer())
                        .subscribeOn(Schedulers.io())
            }
        }
    }
}