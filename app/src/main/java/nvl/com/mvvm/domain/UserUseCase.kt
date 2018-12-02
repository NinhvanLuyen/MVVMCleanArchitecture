package nvl.com.mvvm.domain

import io.reactivex.schedulers.Schedulers
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.data.local.room_repository.UserRepo
import nvl.com.mvvm.data.remote.api.ApiService

class UserUseCase(private val apiServices: ApiService, private val userRepo: UserRepo) {
    fun getListUser(page: Int) =
            getListBookMarkUser()
                    .concatMap { listUserBookmarked ->
                        apiServices.getListUser(page).toObservable()!!
                                .doOnNext {
                                    if (listUserBookmarked.isNotEmpty())
                                        for (user in listUserBookmarked) {
                                            for (u in it.getDatas()!!) {
                                                if (u.user_id == user.user_id)
                                                    u.isBookmark = user.isBookmark
                                            }
                                        }
                                }
                    }

    fun bookMarkUser(user: User) = userRepo.insert(user).toObservable().subscribeOn(Schedulers.io())
    fun getListBookMarkUser() = userRepo.getAll().toObservable().subscribeOn(Schedulers.io())
    fun unBookMarkUser(user: User) = userRepo.deleteUser(user).toObservable().subscribeOn(Schedulers.io())
    fun getLiveDataUser() = userRepo.getAllLiveData()
}
