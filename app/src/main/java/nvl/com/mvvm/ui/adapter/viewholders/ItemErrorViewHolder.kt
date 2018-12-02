package nvl.com.mvvm.ui.adapter.viewholders

import android.databinding.DataBindingUtil
import android.view.View
import nvl.com.mvvm.databinding.ItemErrorBinding
import nvl.com.mvvm.ui.adapter.DelegateUser

class ItemErrorViewHolder(view: View,var delegateUser: DelegateUser) : BaseViewHolder(view) {
    lateinit var viewBinding: ItemErrorBinding
    override fun onBind() {
    }

    override fun bindData(data: Any, position: Int) {
        viewBinding = DataBindingUtil.bind(itemView)!!
        viewBinding.btnTryAgain.setOnClickListener { delegateUser.retry() }
    }

    override fun onClick(v: View?) {
    }

}