package nvl.com.mvvm.services.apierror

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiException(val apiError: ApiError) : RuntimeException()