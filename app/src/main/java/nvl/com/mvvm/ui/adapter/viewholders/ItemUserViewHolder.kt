package nvl.com.mvvm.ui.adapter.viewholders

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.view.View
import nvl.com.mvvm.databinding.ItemUserBinding
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.ui.adapter.DelegateUser
import nvl.com.mvvm.utils.Utils

class ItemUserViewHolder(view: View, var delegate: DelegateUser) : BaseViewHolder(view) {
    var avatar = ObservableField<String>()
    var name = ObservableField<String>()
    var reputation = ObservableField<String>()
    var localtion = ObservableField<String>()
    private lateinit var user: User
    lateinit var viewBinding: ItemUserBinding
    override fun onBind() {

    }

    override fun bindData(data: Any, position: Int) {
        user = data as User
        avatar.set(user.profile_image)
        name.set(user.display_name)
        reputation.set(Utils.getShortNumberFormat(user.reputation))
        localtion.set(user.location)
        viewBinding = DataBindingUtil.bind(itemView)!!
        viewBinding.checkbox.isChecked = user.isBookmark
        viewBinding.checkbox.setOnCheckedChangeListener { _, ischecked ->
            user.isBookmark = ischecked
            delegate.bookmarkUser(user, ischecked)
        }
        viewBinding.viewHolder = this
    }

    override fun onClick(p0: View?) {
        delegate.viewUserDetail(user)

    }

}