package nvl.com.mvvm.data.remote.base_model

class LoadMoreData<T> {
    private var items:ArrayList<T>?  = arrayListOf<T>()
    private var quota_max = 0
    private var quota_remaining = 0
    private var has_more = false
    private var error_id = 0
    private var error_message = ""
    private var error_name = ""
    fun getHasMore() = has_more
    fun getDatas() = items
    fun getErrorID() = error_id
    fun getErrors(): String = error_message

}