package nvl.com.mvvm.ui.adapter

import android.view.View
import nvl.com.mvvm.R
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.ui.adapter.viewholders.BaseViewHolder
import nvl.com.mvvm.ui.adapter.viewholders.ItemErrorViewHolder
import nvl.com.mvvm.ui.adapter.viewholders.ItemLoadingViewHolder
import nvl.com.mvvm.ui.adapter.viewholders.ItemUserViewHolder
import timber.log.Timber

class UserAdapter(var delegate: DelegateUser) : BaseAdapter() {
    private val DATA = 0
    private val LOAD = 1
    private val ERROR = 2

    init {
        insertSection(DATA, mutableListOf<User>())
        insertSection(LOAD, mutableListOf<Any>())
        insertSection(ERROR, mutableListOf<Any>())


    }

    override fun layout(sectionRow: SectionRow): Int {
        return if (sectionRow.section == DATA)
            R.layout.item_user
        else if (sectionRow.section == ERROR)
            R.layout.item_error
        else
            R.layout.item_loading
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return if (layout == R.layout.item_user)
            ItemUserViewHolder(view, delegate)
        else if (layout == R.layout.item_error)
            ItemErrorViewHolder(view, delegate)
        else
            ItemLoadingViewHolder(view)
    }

    fun addData(data: List<User>, loadMore: Boolean) {
        if (data.isNotEmpty()) {
            val currentPosition = sections[DATA].size + 1
            (sections[DATA] as MutableList<User>).addAll(data)
            if (loadMore) {
                if (sections[ERROR].isNotEmpty()) {
                    setSection(ERROR, mutableListOf<Any>())
                    notifyItemRemoved(currentPosition)
                }
                if (sections[LOAD].isEmpty()) {
                    (sections[LOAD] as MutableList<Any>).add(Any())
                    notifyItemRangeInserted(currentPosition, data.size + 1)
                } else {
                    notifyItemRangeInserted(currentPosition, data.size)
                }
            } else {
                if (sections[LOAD].isEmpty()) {
                    notifyItemRangeInserted(currentPosition, data.size)
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
    fun addData(datas: MutableList<User>) {
        if (datas.isNotEmpty()) {
            setSection(DATA, datas)
            notifyDataSetChanged()
        }
    }

    fun addErrorItem() {
        val currentPosition = sections[DATA].size + 1
        setSection(LOAD, mutableListOf<Any>())
        notifyItemRemoved(currentPosition)
        if (sections[ERROR].isEmpty()) {
            (sections[ERROR] as MutableList<Any>).add(Any())
            notifyItemRangeInserted(currentPosition, 1)
        }
    }

    fun addLoading() {
        val currentPosition = sections[DATA].size + 1
        setSection(ERROR, mutableListOf<Any>())
        notifyItemRemoved(currentPosition)
        if (sections[LOAD].isEmpty()) {
            (sections[LOAD] as MutableList<Any>).add(Any())
            notifyItemRangeInserted(currentPosition, 1)
        }
    }

    fun removeData() {
        setSection(DATA, mutableListOf<User>())
        setSection(LOAD, mutableListOf<Any>())
        setSection(ERROR, mutableListOf<Any>())
        notifyDataSetChanged()
    }

    fun notifySingleItemChange(userViewDetail: User, position: Int) {
        Timber.e("data changed at $position checked is ${userViewDetail.isBookmark}")
        (sections[DATA] as MutableList<User>)[position] = userViewDetail
        notifyItemChanged(position)
    }

    fun searchAndNotifyItemChange(listBookmarked: MutableList<User>) {
        for ((index, user) in (sections[DATA] as MutableList<User>).withIndex()) {
            var isFounded = false
            if (user.isBookmark) {
                for (u in listBookmarked) {
                    if (u.user_id == user.user_id) {
                        isFounded = true
                    }
                }
                if (!isFounded) {
                    (sections[DATA] as MutableList<User>)[index].isBookmark = false
                    notifyItemChanged(index)
                }
            }
        }
    }
    fun searchAndNotifyItemChangeForBookmarkList(listBookmarked: MutableList<User>) {
        if (listBookmarked.size > sections[DATA].size) {
            //add item
            for ((index, user) in listBookmarked.withIndex()) {
                var isFounded = false
                if (user.isBookmark) {
                    for (u in (sections[DATA] as MutableList<User>)) {
                        if (u.user_id == user.user_id) {
                            isFounded = true
                        }
                    }
                    if (!isFounded) {
                        (sections[DATA] as MutableList<User>).add(user)
                        notifyItemInserted((sections[DATA] as MutableList<User>).size)
                    }
                }
            }
        } else {
            //remove item
            var iter = (sections[DATA] as MutableList<User>).iterator()
            var index = 0
            while (iter.hasNext()) {
                var useriter = iter.next()
                var ufounded = listBookmarked.find { useriter.user_id == it.user_id }
                if (ufounded == null) {
                    iter.remove()
                    notifyItemRemoved(index)
                }
                index++
            }
        }
    }
    fun getSizeList() =sections[DATA].size

}

interface DelegateUser {
    fun viewUserDetail(user: User)
    fun bookmarkUser(user: User, isChecked: Boolean)
    fun retry()
}
