package nvl.com.mvvm.ui.adapter.viewholders

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.view.View
import nvl.com.mvvm.data.entities.Reputation
import nvl.com.mvvm.databinding.ItemReputationBinding
import nvl.com.mvvm.utils.Utils

class ItemReputationViewHolder(view: View) : BaseViewHolder(view) {
    lateinit var reputation: Reputation
    var reputationType = ObservableField<String>()
    var changeHistory = ObservableField<String>()
    var date = ObservableField<String>()
    var postId = ObservableField<String>()
    lateinit var viewBinding: ItemReputationBinding

    override fun onBind() {

    }

    override fun bindData(data: Any, position: Int) {
        reputation = data as Reputation
        reputationType.set(reputation.reputation_history_type)
        date.set(Utils.convertLongToStringDate(reputation.creation_date))
        changeHistory.set("${reputation.reputation_change}")
        postId.set("${reputation.post_id}")
        viewBinding = DataBindingUtil.bind(itemView)!!
        viewBinding.viewHolder = this
    }

    override fun onClick(p0: View?) {

    }

}