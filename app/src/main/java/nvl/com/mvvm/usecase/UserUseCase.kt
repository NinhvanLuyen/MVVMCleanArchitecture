package nvl.com.mvvm.usecase

import nvl.com.mvvm.services.api.ApiService
import nvl.com.mvvm.services.local.LocalService

class UserUseCase(private val apiServices: ApiService, private val localService: LocalService) {
    fun getListUser(page: Int) = apiServices.getListUser(page).toObservable()

}
