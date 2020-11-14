package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import timber.log.Timber


class AllThingsViewModel @ViewModelInject constructor(private val repository: tf.samir.domain.repository.ThingRepository) : ViewModel() {
    companion object {
        const val TAG = "AllThingsViewModel"
        enum class Action {
            Idle, NavigateToCreateBorrowing
        }
    }

    val things = liveData {
        repository.allThings.map { thingList -> thingList.map { it.toRecyclerItem() } }
            .collect { emit(it) }
    }

    private val _event = MutableLiveData(Action.Idle)
    val event: LiveData<Action>
        get() = _event

    init {
        Timber.tag(TAG).i( "$TAG created!")
    }

    fun onNavigateToCreateBorrowing() {
        _event.value = Action.NavigateToCreateBorrowing
    }

    fun onActionComplete() {
        _event.value = Action.Idle
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).i("$TAG destroyed!")
    }

}


