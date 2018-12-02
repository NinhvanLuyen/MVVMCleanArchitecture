package nvl.com.mvvm.ui.views

import android.os.Bundle
import nvl.com.mvvm.R
import nvl.com.mvvm.ui.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel.ViewModel>(MainViewModel.ViewModel::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListUserFragment.newInstance())
                    .commitNow()
        }
    }

}
