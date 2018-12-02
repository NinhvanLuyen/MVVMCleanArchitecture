package nvl.com.mvvm.ui.views

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.list_user_fragment.*
import nvl.com.mvvm.R
import nvl.com.mvvm.databinding.ListUserFragmentBinding
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.ui.adapter.DelegateUser
import nvl.com.mvvm.ui.adapter.RecyclerViewPaginator
import nvl.com.mvvm.ui.adapter.UserAdapter
import nvl.com.mvvm.ui.viewmodel.ListUserViewModel
import timber.log.Timber

class ListUserFragment : BaseFragment<ListUserViewModel.ViewModel>(ListUserViewModel.ViewModel::class), DelegateUser {
    override fun bookmarkUser(user: User, isChecked: Boolean) {
        viewModel.input.bookMarkUser(user, isChecked)
    }

    override fun viewUserDetail(user: User) {
        Timber.e("Navigation_to_UserDEtail")
        view!!.findNavController().navigate(R.id.to_detail,UserDetailFragment.getBundle(user))
    }

    override fun retry() {
        adapter.addLoading()
        viewModel.input.retryOnErrorItem()
    }

    companion object {
        fun newInstance() = ListUserFragment()
    }

    lateinit var viewBinding: ListUserFragmentBinding
    private var adapter = UserAdapter(this)
    private var adapterBookmarked = UserAdapter(this)
    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.list_user_fragment, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.recyclerView.adapter = adapter
        var layoutManager = LinearLayoutManager(activity)
        var ll = LinearLayoutManager(activity)
        viewBinding.recyclerViewBookmarked.adapter = adapterBookmarked
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerViewBookmarked.layoutManager = ll
        recyclerViewPaginator = RecyclerViewPaginator(viewBinding.recyclerView) { viewModel.input.nextPage() }
        var observable = android.arch.lifecycle.Observer<List<User>> {
            adapter.searchAndNotifyItemChange(it!!.toMutableList())
            adapterBookmarked.searchAndNotifyItemChangeForBookmarkList(it.toMutableList())
        }

        viewModel.getListLiveData().observe(this, observable)

        viewModel.error.addItemErrorBottomList()
                .compose(bindTolifecycle())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it)
                        adapter.addErrorItem()
                    else
                        adapter.addLoading()
                }
        viewModel.output.renderListUser()
                .compose(bindTolifecycle())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.addData(it.first, it.second)
                }
        viewModel.output.renderListUserBookmarked()
                .compose(bindTolifecycle())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapterBookmarked.addData(it)
                }
        viewBinding.swipeRefresh.setOnRefreshListener {
            if (!showOnlyBookmarked.isChecked) {
                adapter.removeData()
                recyclerViewPaginator.start()
                viewModel.input.swipeRefresh()
            } else {
                viewBinding.swipeRefresh.isRefreshing = false
            }
        }
        viewBinding.btnRetry.setOnClickListener {
            adapter.removeData()
            recyclerViewPaginator.start()
            viewModel.input.swipeRefresh()
        }
        viewBinding.showOnlyBookmarked.setOnCheckedChangeListener { _, ischecked ->
            if (ischecked) {
                viewBinding.recyclerViewBookmarked.visibility = View.VISIBLE
                viewBinding.recyclerView.visibility = View.GONE
            } else {
                viewBinding.recyclerViewBookmarked.visibility = View.GONE
                viewBinding.recyclerView.visibility = View.VISIBLE

            }
        }
        return viewBinding.root
    }

    lateinit var recyclerViewPaginator: RecyclerViewPaginator

    fun size(s: String): Int {
        return s.toIntOrNull() ?: 0
    }

}