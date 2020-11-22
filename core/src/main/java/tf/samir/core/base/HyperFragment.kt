package tf.samir.core.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import timber.log.Timber

abstract class HyperFragment<STATE, EFFECT, EVENT, ViewModel: HyperViewModel<STATE, EFFECT, EVENT>> : Fragment() {

    companion object {
        const val TAG = "HyperActivity"
    }

    abstract val viewModel: ViewModel

    private val viewStateObsever = Observer<STATE> {
        Timber.tag(TAG).d("observed viewState : $it")
        renderViewState(it)
    }

    abstract fun renderViewState(viewState: STATE)

    private val viewEffectObserver = Observer<EFFECT> {
        Timber.tag(TAG).d("observed viewEffect : $it")
        renderViewEffect(it)
    }

    abstract fun renderViewEffect(viewEffect: EFFECT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Registering observers
        viewModel.viewStates.observe(this, viewStateObsever)
        viewModel.viewEffects.observe(this, viewEffectObserver)
    }

}