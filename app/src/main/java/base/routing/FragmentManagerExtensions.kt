package base.routing

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.inTransaction(
    action: FragmentTransaction.() -> FragmentTransaction
) = onMainThread {
    beginTransaction()
        .action()
        .commitNow()
}

private fun onMainThread(action: () -> Unit) = with(Looper.getMainLooper()) {
    if (Looper.myLooper() == this) action() else Handler(this).post(action)
}
