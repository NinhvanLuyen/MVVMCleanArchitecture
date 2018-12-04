package nvl.com.mvvm.ui.adapter

import android.view.View
import nvl.com.mvvm.R
import nvl.com.mvvm.data.entities.Reputation
import nvl.com.mvvm.ui.adapter.viewholders.BaseViewHolder
import nvl.com.mvvm.ui.adapter.viewholders.ItemErrorViewHolder
import nvl.com.mvvm.ui.adapter.viewholders.ItemLoadingViewHolder
import nvl.com.mvvm.ui.adapter.viewholders.ItemReputationViewHolder

class ReputationAdapter(var delegateUser: DelegateUser) : BaseAdapter() {
    private val DATA = 0
    private val LOAD = 1
    private val ERROR = 2

    init {
        insertSection(DATA, mutableListOf<Reputation>())
        insertSection(LOAD, mutableListOf<Any>())
        insertSection(ERROR, mutableListOf<Any>())

    }

    override fun layout(sectionRow: SectionRow): Int = when {
        sectionRow.section == DATA -> R.layout.item_reputation
        sectionRow.section == ERROR -> R.layout.item_error
        else -> R.layout.item_loading
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = when (layout) {
        R.layout.item_reputation -> ItemReputationViewHolder(view)
        R.layout.item_error -> ItemErrorViewHolder(view, delegateUser)
        else -> ItemLoadingViewHolder(view)
    }

    fun addData(datas: List<Reputation>, loadMore: Boolean) {
        if (datas.isNotEmpty()) {
            val currentPosition = sections[DATA].size + 1
            (sections[DATA] as MutableList<Reputation>).addAll(datas)
            if (loadMore) {
                if (sections[LOAD].isEmpty()) {
                    (sections[LOAD] as MutableList<Any>).add(Any())
                    notifyItemRangeInserted(currentPosition, datas.size + 1)
                } else {
                    notifyItemRangeInserted(currentPosition, datas.size)
                }
            } else {
                if (sections[LOAD].isEmpty()) {
                    notifyItemRangeInserted(currentPosition, datas.size)
                } else {
                    setSection(LOAD, mutableListOf<Any>())
                    notifyDataSetChanged()
                }
            }
        } else {
            if (!loadMore) {
                sections.set(LOAD, mutableListOf<Any>())
                notifyDataSetChanged()
            }
        }
    }

    fun removeData() {
        setSection(DATA, mutableListOf<Reputation>())
        setSection(LOAD, mutableListOf<Any>())
        notifyDataSetChanged()
    }
}