package nvl.com.mvvm.di


import nvl.com.mvvm.ui.viewmodel.ListUserViewModel
import nvl.com.mvvm.ui.viewmodel.UserDetailViewModel
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module

val appModule = module {
    viewModel { ListUserViewModel.ViewModel(get()) }
    viewModel { UserDetailViewModel.ViewModel(get()) }

}

