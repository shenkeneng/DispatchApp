package com.frxs.ktdispatch.listener

import android.content.Context
import com.frxs.ktdispatch.widget.LoadingDialog

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/07/03
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
interface LoadingDialogManager {

    val loadingDialog: LoadingDialog

    fun showLoadingDialog(context: Context) {
        loadingDialog.showDialog(context, null, true, null)
    }

    fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }
}