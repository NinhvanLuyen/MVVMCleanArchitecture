package nvl.com.mvvm.ui.main

import android.databinding.ObservableField
import nvl.com.mvvm.libs.BaseViewModel
import nvl.com.mvvm.usecase.UserUseCase
import timber.log.Timber

interface MainViewModel {
    class Data {
        var name = ObservableField<String>("name_default")
    }


    class ViewModel(userUseCase: UserUseCase) : BaseViewModel() {
        var data = Data()

        init {
            data.name.set("Luyen")
            userUseCase.getListUser(1)
                    .subscribe {
                        for (i in it.getDatas())
                            Timber.e("${i.account_id}")
                    }
        }

    }
}

