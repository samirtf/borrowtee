package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tf.samir.borrowtee.features.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.usecases.GetThingsUseCase
import timber.log.Timber


class AllThingsVM @ViewModelInject constructor(private val getThingsUseCase: GetThingsUseCase)
    : HyperViewModel<AllThingsViewState, AllThingsEffect, AllThingsEvent>() {
    companion object {
        const val TAG = "AllThingsViewModel"
        enum class Action {
            Idle, NavigateToCreateBorrowing
        }
    }

    private var count: Int = 0

    init {
        Timber.tag(TAG).i( "$TAG created!")
        viewState = AllThingsViewState(fetchStatus = FetchStatus.NotFetched, allThings = emptyList())
    }

    val things = liveData<List<RecyclerItem>> {
        val result = getThingsUseCase.invoke()
        result.getOrDefault(flowOf(emptyList<ThingEntity>())).map { thingList -> thingList.map { it.toRecyclerItem() } }.collect{ v -> emit(v)}
    }

    override fun handle(viewEvent: AllThingsEvent) {
        super.handle(viewEvent)
        when (viewEvent) {
            is AllThingsEvent.FabClicked -> fabClicked()
            AllThingsEvent.FetchAllThings -> fetchAllThings()
        }
    }

    private fun fabClicked() {
        Timber.tag(TAG).i( "$TAG FabClicked!")
        viewEffect = AllThingsEffect.ShowToast(message = "Fab clicked count $count")
    }

    private fun fetchAllThings() {
        Timber.tag(TAG).i( "$TAG FetchAllThings!")
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            getThingsUseCase.invoke().fold({ flow ->
                flow.collect { data ->
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, allThings = data.map { it.toRecyclerItem() })
                }
            }, {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                viewEffect = AllThingsEffect.ShowToast(message = "Unable to update things...")
            })
        }
    }

    private val _event = MutableLiveData(Action.Idle)
    val event: LiveData<Action>
        get() = _event

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


