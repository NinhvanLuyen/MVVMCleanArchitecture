package nvl.com.mvvm.ui.viewmodel

import nvl.com.mvvm.usecase.UserUseCase

interface MainViewModel {

    class ViewModel(userUseCase: UserUseCase) : BaseViewModel(userUseCase) {

    }
}
