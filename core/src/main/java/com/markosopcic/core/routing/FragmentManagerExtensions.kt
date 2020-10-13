package com.markosopcic.core.routing

import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.addFragment(@IdRes containerId: Int, fragment: Fragment, additionalAction: FragmentTransaction.() -> FragmentTransaction = { this }, addToBackStack: Boolean = true) {
    onMainThread {
        if (addToBackStack) {
            beginTransaction()
                .additionalAction()
                .add(containerId, fragment, fragment::class.java.simpleName)
                .addToBackStack(fragment::class.java.simpleName)
                .commit()
            executePendingTransactions()
        } else {
            beginTransaction()
                .add(containerId, fragment, fragment::class.java.simpleName)
                .additionalAction()
                .commitNow()
        }
    }
}

fun FragmentManager.removeFragment(tag: String) = onMainThread {
    findFragmentByTag(tag)?.let {
        beginTransaction().remove(it).commitNow()
    }
}

private fun onMainThread(action: () -> Unit) = with(Looper.getMainLooper()) {
    if (Looper.myLooper() == this) action() else Handler(this).post(action)
}
