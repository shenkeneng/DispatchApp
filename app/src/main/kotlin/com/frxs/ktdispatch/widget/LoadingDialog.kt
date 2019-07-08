package com.frxs.ktdispatch.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.frxs.dispatch.R

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/07/03
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
class LoadingDialog : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    fun showDialog(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): LoadingDialog {
        return showDialog(context, null, cancelable, cancelListener)
    }

    fun showDialog(context: Context, message: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): LoadingDialog {
        var dialog = LoadingDialog(context)
        dialog.setContentView(R.layout.view_loading_progress)
        var messageTv = dialog.findViewById<TextView>(R.id.current_action)
        if (TextUtils.isEmpty(message)) {
            messageTv.visibility = View.GONE
        } else {
            messageTv.text = message
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(cancelable)
        dialog.setOnCancelListener(cancelListener)
        val lp = dialog.window.attributes
        lp.dimAmount = 0.2f
        dialog.window.attributes = lp
        dialog.show()
        return dialog
    }
}