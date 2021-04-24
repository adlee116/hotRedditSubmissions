package com.presentation.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

abstract class BaseActivity : AppCompatActivity(), BaseFragment.Callback {

    @Nullable
    var lifecycleOwner: ViewLifecycleOwner? = null

    override fun onNavigateTo(intentDetails: Intent) {
        navigateTo(intentDetails)
    }

    override fun onNavigateTo(activity: Class<out BaseActivity>, intentDetails: Intent.() -> Unit) {
        navigateTo(activity, intentDetails)
    }

    override fun onNavigateTo(activity: Class<out BaseActivity>, resultCode: Int, intentDetails: Intent.() -> Unit) {
        navigateTo(activity, resultCode, intentDetails)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleOwner = ViewLifecycleOwner()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        lifecycleOwner = null
        super.onDestroy()
    }

    fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        onActivityResult(requestCode, resultCode, data)
    }

    fun navigateTo(activity: Class<out BaseActivity>, intentDetails: Intent.() -> Unit = {}) {
        startActivity(
            Intent(this, activity).also {
                it.intentDetails()
            }
        )
    }

    fun navigateTo(intentDetails: Intent) {
        startActivity(intentDetails)
        if ((intentDetails.flags and Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
            finish()
        }
    }

    fun navigateTo(activity: Class<out BaseActivity>) = navigateTo(activity) {}

    fun navigateTo(activity: Class<out BaseActivity>, resultCode: Int, intentDetails: Intent.() -> Unit = {}) {
        startActivityForResult(Intent(this, activity).also { it.intentDetails() }, resultCode)
    }

    fun navigateTo(activity: Class<out BaseActivity>, resultCode: Int) = navigateTo(activity, resultCode) {}

    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, length).show()

    class ViewLifecycleOwner : LifecycleOwner {

        private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

        val lifecycle: LifecycleRegistry
            get() = lifecycleRegistry

        override fun getLifecycle(): Lifecycle = lifecycleRegistry
    }

}
