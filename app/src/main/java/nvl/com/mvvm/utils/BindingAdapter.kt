package nvl.com.mvvm.utils

import android.support.v4.widget.SwipeRefreshLayout
import com.facebook.drawee.view.SimpleDraweeView

object BindingAdapter {
    @JvmStatic
    @android.databinding.BindingAdapter("app:imageUrl")
    fun loadImage(view: SimpleDraweeView, imageUrl: String?) {
        if (imageUrl!!.isNotEmpty())
            view.setImageURI(imageUrl)
    }

    @JvmStatic
    @android.databinding.BindingAdapter("app:isRefresh")
    fun isRefreshing(view: SwipeRefreshLayout, isRefresh: Boolean?) {
        view.isRefreshing = isRefresh ?: false
    }
}