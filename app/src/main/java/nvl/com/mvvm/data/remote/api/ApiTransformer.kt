package nvl.com.mvvm.data.remote.api

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import nvl.com.mvvm.utils.ErrorCodes
import nvl.com.mvvm.data.remote.base_model.LoadMoreData

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiTransformer<T> : SingleTransformer<LoadMoreData<T>, LoadMoreData<T>> {
    override fun apply(upstream: Single<LoadMoreData<T>>): SingleSource<LoadMoreData<T>> {


        return upstream.flatMap { loadMoreUser: LoadMoreData<T> ->
            if (loadMoreUser.getDatas() !=null)
                Single.create<LoadMoreData<T>> { t ->
                    if (loadMoreUser.getDatas() != null)
                        t.onSuccess(loadMoreUser)
                    else {
                        t.onError(ApiException(ApiError(ErrorCodes.NO_DATA_RESULT, "No Data")))
                    }
                }
            else
                Single.create<LoadMoreData<T>> { t -> t.onError(ApiException(ApiError(loadMoreUser.getErrorID(), loadMoreUser.getErrors()))) }
        }
    }
}