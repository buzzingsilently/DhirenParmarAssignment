package com.buzzingsilently.dhirenparmarassignment.utility

import android.view.View
import android.widget.TextView
import com.buzzingsilently.dhirenparmarassignment.R
import java.util.regex.Pattern

open class Validator(private val view: View) {

    open fun isValidFullName(): String {
        var error = ""
        return if (view is TextView) {
            val fullName = view.getTextValue()
            if (fullName.isBlank()) {
                error = view.context.getString(R.string.error_empty_full_name)
                error
            } else {
                error
            }
        } else {
            error
        }
    }

    open fun isValidEmail(): String {
        var error = ""
        if (view is TextView) {
            val email = view.getTextValue()
            if (email.isBlank()) {
                error = view.context.getString(R.string.error_empty_email)
                return error
            }

            val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return if (matcher.matches()) {
                error
            } else {
                error = view.context.getString(R.string.error_valid_email)
                error
            }
        } else {
            return error
        }
    }

    // @param isCheckPattern : true when password pattern has to be matched with GitHub password pattern
    open fun isValidPassword(isCheckPattern: Boolean): String {
        var error = ""
        if (view is TextView) {
            val password = view.getTextValue()
            if (password.isBlank()) {
                error = view.context.getString(R.string.error_empty_password)
                return error
            }

            return if (isCheckPattern) {
                val expression = "[a-z0-9]{8,30}"
                val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
                val matcher = pattern.matcher(password)
                if (matcher.matches()) {
                    error
                } else {
                    error = view.context.getString(R.string.error_valid_password)
                    error
                }
            } else {
                error
            }
        } else {
            return error
        }
    }

    open fun isValidConfirmPassword(password: String): String {
        var error = ""
        if (view is TextView) {
            val confirmPassword = view.getTextValue()
            return if (confirmPassword.isBlank() || confirmPassword != password) {
                error = view.context.getString(R.string.error_valid_confirm_password)
                error
            } else {
                error
            }
        } else {
            return error
        }
    }
}