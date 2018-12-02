package nvl.com.mvvm.ui.views

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import nvl.com.mvvm.R
import nvl.com.mvvm.ui.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel.ViewModel>(MainViewModel.ViewModel::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, ListUserFragment.newInstance())
//                    .commitNow()
//        }
        val defaultFragment = NavHostFragment.create(R.navigation.nav_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, defaultFragment)
                    .setPrimaryNavigationFragment(defaultFragment)
                    .commit()
        }
    }

}
