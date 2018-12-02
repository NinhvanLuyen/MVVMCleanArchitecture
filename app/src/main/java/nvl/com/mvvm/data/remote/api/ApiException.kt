package nvl.com.mvvm.data.remote.api

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiException(val apiError: ApiError) : RuntimeException()