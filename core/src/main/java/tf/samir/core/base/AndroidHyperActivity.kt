package tf.samir.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import timber.log.Timber

/**
 * @author Rohit Surwase
 * @author https://github.com/RohitSurwase
 */
abstract class AndroidHyperActivity<STATE, EFFECT, EVENT, ViewModel: AndroidHyperViewModel<STATE, EFFECT, EVENT>> : AppCompatActivity() {

    companion object {
        const val TAG = "AndroidHyperActivity"
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