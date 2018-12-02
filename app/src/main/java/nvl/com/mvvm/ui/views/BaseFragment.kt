package nvl.com.mvvm.ui.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nvl.com.mvvm.ui.dialog.DialogManager
import nvl.com.mvvm.ui.viewmodel.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModelByClass
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseFragment<out T : BaseViewModel>(viewModelClass: KClass<T>) : Fragment() {
    open val viewModel: T by viewModelByClass(viewModelClass)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addObserver(lifecycle)
        viewModel.setArgument(arguments)
        Timber.e("onViewCreated")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("onDestroy")
        viewModel.removeObserver(lifecycle)
    }
    fun <T> bindTolifecycle(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
    protected fun showErrorMessage(message: String) {
        if (message.isNotEmpty()) {
            DialogManager.showDialogMessage(message, context!!)
        }
    }
}
