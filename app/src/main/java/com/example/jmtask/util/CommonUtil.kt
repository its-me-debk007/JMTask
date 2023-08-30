package com.example.jmtask.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

const val MOVIE_DB = "MovieDB"
const val NO_INTERNET = "No Internet Connection"

fun hideKeyboard(view: View, activity: Activity?) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
