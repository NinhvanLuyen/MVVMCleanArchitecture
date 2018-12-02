package nvl.com.mvvm.ui.viewmodel

import nvl.com.mvvm.domain.UserUseCase

interface MainViewModel {

    class ViewModel(userUseCase: UserUseCase) : BaseViewModel(userUseCase) {

    }
}
