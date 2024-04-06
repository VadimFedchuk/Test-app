package com.example.ezlotestapp.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Context.getImageResourceIdByName(name: String):Int {
    return resources.getIdentifier(
        name,
        "drawable",
        this.packageName
    )
}

fun View.showKeyboard() {
    this.requestFocus()
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}