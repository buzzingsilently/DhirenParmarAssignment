package com.buzzingsilently.dhirenparmarassignment.utility

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.TIME_INTERVAL
import com.google.android.material.snackbar.Snackbar
import java.util.*

object Utility {

    //check date given as param is due or not
    //current interval is 2hours
    fun isTimeDue(date: Date) : Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, TIME_INTERVAL)
        return calendar.after(Calendar.getInstance())
    }

    //check is network connection available or not
    fun hasNetwork(view: View, context: Context) : Boolean{
        val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return if (networkInfo!=null && networkInfo.isConnected) {
            true
        } else {
            showSnackBar(view, context.getString(R.string.error_no_connection))
            false
        }
    }

    fun hideKeyboard() {

    }

    //display snackbar on screen
    fun showSnackBar(v: View, msg: String) {
        val mSnackBar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG)
        val view = mSnackBar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        //  params.gravity = Gravity.TOP
        view.layoutParams = params
        view.setBackgroundColor(Color.DKGRAY)
        val mainTextView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        mainTextView.setTextColor(Color.WHITE)
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, v.context.resources.getDimension(R.dimen.ftMedium))
        mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        mainTextView.maxLines = 4
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mSnackBar.show()
    }
}