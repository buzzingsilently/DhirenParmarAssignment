package com.buzzingsilently.dhirenparmarassignment.utility

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

fun TextView.getTextValue() = this.text.toString().trim()

fun TextInputLayout.isValidationNotSatisfied(error: String): Boolean {
    return if (error.isEmpty()) {
        this.isErrorEnabled = false
        false
    } else {
        this.error = error
        true
    }
}

fun View.hideKeyboard() {
    val inputMethodManager = this.context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}