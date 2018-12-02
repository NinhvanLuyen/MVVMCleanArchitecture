package nvl.com.mvvm.ui.views

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseActivity<T : ViewModel>(clazz : KClass<T>):AppCompatActivity(){
    open val viewModel: T by viewModelByClass(clazz)

}