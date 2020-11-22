package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import tf.samir.borrowtee.features.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.domain.usecases.GetThingsUseCase
import timber.log.Timber


class AllThingsViewModel @ViewModelInject constructor(private val getThingsUseCase: GetThingsUseCase) : ViewModel() {
    companion object {
        const val TAG = "AllThingsViewModel"
        enum class Action {
            Idle, NavigateToCreateBorrowing
        }
    }

//    val things = liveData<List<RecyclerItem>> {
//        getThingsUseCase.invoke().map { thingList -> thingList.map { it.toRecyclerItem() } }.collect { emit(it) }
//    }

    val things = liveData<List<RecyclerItem>> {
        val result = getThingsUseCase.invoke()
        result.fold({flow ->
            flow.map { thingList -> thingList.map { it.toRecyclerItem() } }.collect{ v -> emit(v)}
        },{
            emit(emptyList<RecyclerItem>())
        })

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


