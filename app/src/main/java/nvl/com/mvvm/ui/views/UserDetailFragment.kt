package nvl.com.mvvm.ui.views

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import nvl.com.mvvm.R
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.databinding.FragmentUserDetailBinding
import nvl.com.mvvm.ui.viewmodel.UserDetailViewModel

class UserDetailFragment : BaseFragment<UserDetailViewModel.ViewModel>(UserDetailViewModel.ViewModel::class) {

    companion object {
        fun getBundle(user: User) = Bundle().apply {
            putParcelable("user", user)

        }
    }
    lateinit var viewBinding: FragmentUserDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        viewBinding.viewModel = viewModel
        return viewBinding.root
    }


}
