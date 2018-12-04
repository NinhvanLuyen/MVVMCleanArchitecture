package nvl.com.mvvm.ui.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nvl.com.mvvm.ui.dialog.DialogManager
import nvl.com.mvvm.ui.viewmodel.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModelByClass
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseFragment<out T : BaseViewModel>(viewModelClass: KClass<T>) : Fragment() {
    open val viewModel: T by viewModelByClass(viewModelClass)
    open val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.addObserver(lifecycle)
        viewModel.setArgument(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.e("onViewCreated")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("onDestroy")
        disposables.dispose()
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
