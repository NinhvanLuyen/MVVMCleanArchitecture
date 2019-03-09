package nvl.com.mvvm.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
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
        disposables.clear()
        NavHostFragment.findNavController(this).navigate(R.id.to_detail, UserDetailFragment.getBundle(user))
    }

    override fun retry() {
        adapter.addLoading()
        viewModel.input.retryOnErrorItem()

    }

    lateinit var viewBinding: ListUserFragmentBinding
    private var adapter = UserAdapter(this)
    private var adapterBookmarked = UserAdapter(this)
    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.list_user_fragment, container, false)
        viewBinding.viewModel = viewModel
        Timber.e("onCreateView")

        return viewBinding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Timber.e("onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.e("onDetach")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("onViewCreated")
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
        disposables.add(
                viewModel.error.addItemErrorBottomList()
                        .compose(bindTolifecycle())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (it)
                                adapter.addErrorItem()
                            else
                                adapter.addLoading()
                        })
        disposables.add(
                viewModel.output.renderListUser()
                        .compose(bindTolifecycle())
                        .subscribe {
                            visibleNoData(it.first.isEmpty())
                            adapter.addData(it.first, it.second)
                            Timber.e("flow 3: render")

                        })
        disposables.add(viewModel.output.renderListUserBookmarked()
                .compose(bindTolifecycle())
                .subscribe {
                    visibleNoData(it.isEmpty())
                    adapterBookmarked.addData(it)
                })
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
            if (ischecked)
                visibleNoData(adapterBookmarked.getSizeList() == 0)
            else
                visibleNoData(adapter.getSizeList() == 0)
            viewModel.data.showOnlyBookmarked.set(ischecked)

        }
    }

    private fun visibleNoData(isEmpty: Boolean) {
        tvNoData.visibility = if (showOnlyBookmarked.isChecked && isEmpty)
            View.VISIBLE
        else
            View.GONE
    }

    lateinit var recyclerViewPaginator: RecyclerViewPaginator

    fun size(s: String): Int {
        return s.toIntOrNull() ?: 0
    }

    override fun onResume() {
        super.onResume()
        Timber.e("onResume")
        Timber.e("checbox ${viewBinding.showOnlyBookmarked.isChecked}")
    }

    override fun onStop() {
        super.onStop()
        Timber.e("onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("onDestroy")

    }

}