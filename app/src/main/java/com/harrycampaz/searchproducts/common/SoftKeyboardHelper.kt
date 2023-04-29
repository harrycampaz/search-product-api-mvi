package com.harrycampaz.searchproducts.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object SoftKeyboardHelper {

    fun hideSoftKeyboard(view: View?) {
        try {
            val inputManager = view?.context?.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager?
            inputManager?.hideSoftInputFromWindow(view?.windowToken, 0)
        } catch (e: Exception) {
        }
    }

}