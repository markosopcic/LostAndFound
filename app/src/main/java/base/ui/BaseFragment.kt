package base.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseFragment<BaseViewState : Any>(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    protected abstract val viewModel: BaseViewModel<BaseViewState>

    protected abstract fun render(viewState: BaseViewState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseView()
        viewModel.viewStates().forEach { disposables.add(it.subscribe(::render, Timber::e)) }
    }

    protected open fun initialiseView() {}

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}
