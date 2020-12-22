package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tf.samir.borrowtee.features.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.entities.AT_HOME
import tf.samir.domain.entities.BORROWED
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.entities.ThingState
import tf.samir.domain.usecases.DeleteBorrowingUseCase
import tf.samir.domain.usecases.GetThingsUseCase
import timber.log.Timber

class AllThingsViewModel @ViewModelInject constructor(
    private val getThingsUseCase: GetThingsUseCase,
    private val deleteBorrowingUseCase: DeleteBorrowingUseCase
) : HyperViewModel<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent>() {

    companion object {
        const val TAG = "AllThingsViewModel"
    }

    private val _things = MutableLiveData<List<RecyclerItem>>().apply { value = emptyList() }
    val things: LiveData<List<RecyclerItem>>
        get() = _things

    @ThingState
    private var stateFilter: Int? = null

    private var sortState: Boolean = false

    init {
        viewState =
            AllThingsViewState(fetchStatus = FetchStatus.NotFetched, allThings = emptyList())
        Timber.tag(TAG).i("$TAG created!")
    }



    override fun handle(viewEvent: AllThingsViewEvent) {
        super.handle(viewEvent)
        when (viewEvent) {
            is AllThingsViewEvent.FabClicked -> navigateToCreateBorrowing()
            AllThingsViewEvent.FetchAllThings -> fetchAllThings()
            is AllThingsViewEvent.DeleteThingClicked -> deleteThingItem(viewEvent.position)
            AllThingsViewEvent.FilterByAllThings -> filterByAllThings()
            AllThingsViewEvent.FilterByBorrowed -> filterByBorrowed()
            AllThingsViewEvent.FilterByAtHome -> filterByAtHome()
            AllThingsViewEvent.SortUpward -> sortUpward()
            AllThingsViewEvent.SortDownward -> sortDownward()
        }
    }

    private fun filterByAllThings() {
        stateFilter = null
        fetchAllThings(stateFilter)
    }

    private fun filterByBorrowed() {
        stateFilter = BORROWED
        fetchAllThings(stateFilter)
    }

    private fun filterByAtHome() {
        stateFilter = AT_HOME
        fetchAllThings(stateFilter)
    }

    private fun sortUpward() {
        sortState = true
        _things.value = _things.value?.sortedBy { recyclerItem -> recyclerItem.data.state }
    }

    private fun sortByUpward(things: List<RecyclerItem>) =
        things.sortedBy { recyclerItem -> recyclerItem.data.state }

    private fun sortDownward() {
        sortState = false
        _things.value = _things.value?.sortedByDescending { recyclerItem -> recyclerItem.data.state }
    }

    private fun sortByDownward(things: List<RecyclerItem>) =
        things.sortedByDescending { recyclerItem -> recyclerItem.data.state }

    private fun navigateToCreateBorrowing() {
        viewEffect = AllThingsViewEffect.NavigateToCreateBorrowingPage
    }

    private fun fetchAllThings(@ThingState filter: Int? = null) {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getThingsUseCase.invoke(filter).fold({ flow ->
                    handleFetchThingsSuccess(flow)
                }, {
                    handleFetchThingsFailure(it)
                })
            }
        }
    }

    private suspend fun handleFetchThingsSuccess(flow: Flow<List<ThingEntity>>) {
        flow.map { thingList -> thingList.map { it.toRecyclerItem() } }.collect {
            withContext(Dispatchers.Main) {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, allThings = it)
                _things.value = if (sortState) sortByUpward(it) else sortByDownward(it)
            }
            Timber.tag(TAG).d("handleFetchThingsSuccess")
        }
    }

    private suspend fun handleFetchThingsFailure(failure: Throwable) {
        withContext(Dispatchers.Main) {
            viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
            viewEffect = AllThingsViewEffect.ShowFetchThingsFailure(failure)
        }
        Timber.tag(TAG).d("handleFetchThingsFailure")
    }

    private fun deleteThingItem(id: Int) {
        things.value?.run {
            val item = get(id)
            viewModelScope.launch {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
                withContext(Dispatchers.IO) {
                    deleteBorrowingUseCase.invoke(item.data.id).fold({
                        handleDeleteThingSuccess(it)
                    }, {
                        handleDeleteThingFailure(it)
                    })
                }
            }
        }
    }

    private suspend fun handleDeleteThingFailure(failure: Throwable) {
        withContext(Dispatchers.Main) {
            viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
            viewEffect = AllThingsViewEffect.ShowDeleteThingFailure(failure)
        }
        Timber.tag(TAG).d("handleDeleteThingFailure")
    }

    private suspend fun handleDeleteThingSuccess(it: Boolean) {
        withContext(Dispatchers.Main) {
            viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
            viewEffect = AllThingsViewEffect.ShowDeleteThingSuccess
        }
        Timber.tag(TAG).d("$it")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).i("$TAG destroyed!")
    }
}