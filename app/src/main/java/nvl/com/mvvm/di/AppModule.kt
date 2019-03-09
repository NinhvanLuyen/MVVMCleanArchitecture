package nvl.com.mvvm.di


import nvl.com.mvvm.ui.viewmodel.ListUserViewModel
import nvl.com.mvvm.ui.viewmodel.UserDetailViewModel
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module

val appModule = module {

    //Note: we declare our ListUserViewModel class as a viewModel in a module. Koin will give a MyViewModel to the lifecycle ViewModelFactory and help bind it to the current component.
    viewModel { ListUserViewModel.ViewModel(get()) }
    viewModel { UserDetailViewModel.ViewModel(get()) }


    //If Using Presenter instead of ViewModel then
//    Note: we declare our ListUserPresenter class as factory to have a create a new instance each time our Activity will need one.
//    factory { ListUserPresenter(get()) }
}

