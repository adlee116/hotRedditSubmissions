package com.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.presentation.base.BaseActivity
import com.presentation.base.BaseFragment

fun Context.hideKeyboard(currentView: View? = null, dummyFocusable: View? = null) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view_ = if (this is Activity) this.currentFocus else null
    if (view_ == null) {
        view_ = View(this)
    }
    view_.post {
        imm.hideSoftInputFromWindow(view_.windowToken, 0)
        currentView?.clearFocus()
        dummyFocusable?.requestFocus()
    }
}

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }

fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))

// Fragment Transactions
inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().transaction().commit()
}

fun AppCompatActivity.replaceFragment(@IdRes frameId: Int, fragment: Fragment) =
    replaceFragment(frameId, fragment, false)

fun AppCompatActivity.replaceFragment(@IdRes frameId: Int, fragment: Fragment, addToBackStack: Boolean = false) =
    supportFragmentManager.inTransaction {
        if (addToBackStack) addToBackStack(null)
        replace(frameId, fragment)
    }

fun BaseFragment.replaceFragment(@IdRes frameId: Int, fragment: Fragment) =
    replaceFragment(frameId, fragment, false)

fun BaseFragment.replaceFragment(@IdRes frameId: Int, fragment: Fragment, addToBackStack: Boolean = false) =
    childFragmentManager.inTransaction {
        if (addToBackStack) addToBackStack(null)
        replace(frameId, fragment)
    }

fun <T> LiveData<T>.safelyObserve(lifecycleOwner: LifecycleOwner?, observer: Observer<T>) =
    lifecycleOwner?.let { observe(it, observer) }

fun BaseActivity.hideKeyboard(dummyFocusable: View? = null, currentView: View? = null) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view_ = currentFocus
    if (view_ == null) {
        view_ = View(this)
    }
    view_.post {
        imm.hideSoftInputFromWindow(view_.windowToken, 0)
        currentView?.clearFocus()
        dummyFocusable?.requestFocus()
    }
}
