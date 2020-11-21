package tf.samir.core.base

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 * @author https://github.com/RohitSurwase
 */
open class HyperViewModel<STATE, EFFECT, EVENT>(application: Application) :
    ViewModel(), ViewEventHandlerContract<EVENT> {

    companion object {
        const val TAG = "AndroidHyperViewModel"
    }

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    val viewStates: LiveData<STATE>
            get() = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Timber.tag(TAG).d("setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }

    private val _viewEffects: SingleLiveEvent<EFFECT> = SingleLiveEvent()
    val viewEffects: SingleLiveEvent<EFFECT> = _viewEffects

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Timber.tag(TAG).d("setting viewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }

    @CallSuper
    override fun handle(viewEvent: EVENT) {
        if (!viewStates.hasObservers()) {
            throw NoObserverAttachedException("No observer attached. In case of custom View \"startObserving()\" function needs to be called manually.")
        }
        Timber.tag(TAG).d("processing viewEvent: $viewEvent")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).d("onCleared")
    }

}
