package nvl.com.mvvm.di


import nvl.com.mvvm.libs.BaseViewModel
import nvl.com.mvvm.services.api.ApiService
import nvl.com.mvvm.services.local.LocalService
import nvl.com.mvvm.ui.main.MainViewModel
import nvl.com.mvvm.usecase.UserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module

val appModule = module {
    viewModel { MainViewModel.ViewModel(get()) }
}

