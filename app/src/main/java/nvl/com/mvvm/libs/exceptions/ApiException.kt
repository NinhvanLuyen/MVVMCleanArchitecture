package nvl.com.mvvm.libs.exceptions

import nvl.com.mvvm.libs.apierror.ApiError

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiException(val apiError: ApiError) : RuntimeException()