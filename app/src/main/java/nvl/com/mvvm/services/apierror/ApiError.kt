package nvl.com.mvvm.services.apierror
import nvl.com.mvvm.utils.ErrorCodes
import nvl.com.mvvm.utils.ErrorMessage
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiError(private val errorCode: Int, val errorMessage: String) {
    companion object {
        fun fromThrowable(throwable: Throwable): ApiError {
            if (throwable is ApiException) {
                Timber.e("ApiException")
                throwable.printStackTrace()
                return throwable.apiError
            } else if (throwable is SocketTimeoutException || throwable is UnknownHostException) {
                Timber.e("SocketTimeoutException || UnknownHostException")
                return ApiError(ErrorCodes.NET_WORK_PROBLEM, ErrorMessage.NET_WORK_PROBLEM)
            } else if (throwable is HttpException) {
                Timber.e("HttpException")
                return ApiError(throwable.code(), ErrorMessage.NET_WORK_PROBLEM)
            } else {
                Timber.e(throwable, throwable.message)
                Timber.e("Unknow Exception")
                return ApiError(ErrorCodes.NET_WORK_PROBLEM, throwable.message ?: ErrorMessage.NET_WORK_PROBLEM)
            }
        }
    }

    fun maintenance() = errorCode == ErrorCodes.SERVER_MAINTENANCE

    fun updateVersion() = errorCode == ErrorCodes.UPDATE_NEW_VERSION

    fun networkProblem() = errorCode == ErrorCodes.NET_WORK_PROBLEM

    fun invalidToken() = errorCode == ErrorCodes.INVALID_TOKEN

    fun nodataResult() = errorCode == ErrorCodes.NO_DATA_RESULT
}