package nvl.com.mvvm.ui.viewmodel

import android.databinding.ObservableField
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.domain.UserUseCase
import nvl.com.mvvm.utils.Utils

interface UserDetailViewModel {
    class Data {
        var avatar = ObservableField<String>()
        var name = ObservableField<String>()
        var reputation = ObservableField<String>()
        var location = ObservableField<String>()
        var errorMessage = ObservableField<String>()
        var notfound = ObservableField<Boolean>()
        var showLoading = ObservableField<Boolean>()
        var loadDataError = ObservableField<Boolean>()
        var isBoomarked = ObservableField<Boolean>()
        var isRefresh = ObservableField<Boolean>()
    }
    class ViewModel(userUseCase: UserUseCase) : BaseViewModel(userUseCase) {
        private lateinit var user: User
        var data = Data()

        init {
            disposables.add(arguments.compose(bindTolifecycle())
                    .subscribe {
                        user = it.getParcelable("user") ?: User()
                        data.name.set(user.display_name)
                        data.avatar.set(user.profile_image)
                        data.isBoomarked.set(user.isBookmark)
                        data.location.set(user.location)
                        data.reputation.set(Utils.getShortNumberFormat(user.reputation))
                    })
        }
    }
}