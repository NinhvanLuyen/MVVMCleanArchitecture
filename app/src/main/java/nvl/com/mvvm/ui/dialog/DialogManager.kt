package nvl.com.mvvm.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.widget.TextView
import nvl.com.mvvm.R

object DialogManager {
    fun showDialogMessage(message: String, context: Context) {
        val textView = TextView(context, null, R.style.TextViewStyle)
//            textView.typeface = UIUtils.getTypeFace()
        textView.text = message
        val margin = context.resources.getDimension(R.dimen.activity_margin).toInt()
        textView.setPadding(margin, margin, margin, 0)

        AlertDialog.Builder(context)
                .setView(textView)
                .setCancelable(true)
                .setPositiveButton(R.string.OK, { _, _ -> })
                .create().show()
    }
}