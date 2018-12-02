package nvl.com.mvvm.ui.adapter.viewholders

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
/**
 * Created by ninhvanluyen on 1/10/18.
 */
abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    val context: Context
    init {
        view.setOnClickListener(this)
        context = view.context
    }

    abstract fun onBind()

    abstract fun bindData(data: Any, position: Int)

}