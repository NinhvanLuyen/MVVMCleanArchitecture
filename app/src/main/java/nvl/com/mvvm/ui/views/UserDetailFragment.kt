package nvl.com.mvvm.ui.views

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.list_user_fragment.*

import nvl.com.mvvm.R
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.databinding.FragmentUserDetailBinding
import nvl.com.mvvm.ui.adapter.DelegateUser
import nvl.com.mvvm.ui.adapter.RecyclerViewPaginator
import nvl.com.mvvm.ui.adapter.ReputationAdapter
import nvl.com.mvvm.ui.viewmodel.UserDetailViewModel
import nvl.com.mvvm.utils.tranforms.Transformers

class UserDetailFragment : BaseFragment<UserDetailViewModel.ViewModel>(UserDetailViewModel.ViewModel::class), DelegateUser {
    override fun viewUserDetail(user: User) {

    }

    override fun bookmarkUser(user: User, isChecked: Boolean) {

    }

    override fun retry() {

    }

    companion object {
        fun getBundle(user: User) = Bundle().apply {
            putParcelable("user", user)

        }
    }

    lateinit var viewBinding: FragmentUserDetailBinding
    lateinit var recyclerViewPaginator: RecyclerViewPaginator
    private var adapter = ReputationAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        viewBinding.viewModel = viewModel
        var naviControler = NavHostFragment.findNavController(this)
        viewBinding.btnBack.setOnClickListener { naviControler.navigateUp() }
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        viewBinding.recyclerView.adapter = adapter
        recyclerViewPaginator = RecyclerViewPaginator(viewBinding.recyclerView) { viewModel.input.nextPage() }
        disposables.add(viewModel.output.renderReputations()
                .compose(bindTolifecycle())
                .subscribe {
                    adapter.addData(it.first, it.second)
                }!!)
        viewBinding.swipeRefresh.setOnRefreshListener {
            adapter.removeData()
            recyclerViewPaginator.start()
            viewModel.input.swipeRefresh()
        }
        viewBinding.btnRetry.setOnClickListener {
            adapter.removeData()
            recyclerViewPaginator.start()
            viewModel.input.swipeRefresh()
        }
        return viewBinding.root
    }


}
