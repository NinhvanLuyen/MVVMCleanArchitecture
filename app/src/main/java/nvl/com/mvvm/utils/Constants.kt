package nvl.com.mvvm.utils

object Configs {
    val IS_DEBUG = true
    val SERVER_URL = "https://api.stackexchange.com/2.2/"
}

object ErrorCodes {
    val SERVER_MAINTENANCE = -8000
    val UPDATE_NEW_VERSION = -8001
    val NET_WORK_PROBLEM = -8002
    val NO_DATA_RESULT = -8004
    val INVALID_TOKEN = -1504

}

object ErrorMessage {
    val NET_WORK_PROBLEM = "Please check your internet connection."
}