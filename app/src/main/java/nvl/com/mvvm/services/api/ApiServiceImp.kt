package nvl.com.mvvm.services.api

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import nvl.com.mvvm.libs.ErrorCodes
import nvl.com.mvvm.libs.ErrorMessage
import nvl.com.mvvm.libs.apierror.ApiError
import nvl.com.mvvm.libs.exceptions.ApiException
import nvl.com.mvvm.libs.utils.DeviceUtils
import nvl.com.mvvm.services.model.LoadMoreData
import nvl.com.mvvm.services.model.User
import nvl.com.mvvm.services.transformer.ApiTransformer

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
}