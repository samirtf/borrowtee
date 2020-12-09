package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tf.samir.borrowtee.features.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.usecases.GetThingsUseCase
import timber.log.Timber

class AllThingsViewModel @ViewModelInject constructor(private val getThingsUseCase: GetThingsUseCase) :
    HyperViewModel<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent>() {

    companion object {
        const val TAG = "AllThingsViewModel"
    }

    init {
        viewState =
            AllThingsViewState(fetchStatus = FetchStatus.NotFetched, allThings = emptyList())
        Timber.tag(TAG).i("$TAG created!")
    }

    private val _things = MutableLiveData<List<RecyclerItem>>().apply { value = emptyList() }
    val things: LiveData<List<RecyclerItem>>
        get() = _things

    override fun handle(viewEvent: AllThingsViewEvent) {
        super.handle(viewEvent)
        when (viewEvent) {
            is AllThingsViewEvent.FabClicked -> navigateToCreateBorrowing()
            AllThingsViewEvent.FetchAllThings -> fetchAllThings()
        }
    }

    private fun navigateToCreateBorrowing() {
        viewEffect = AllThingsViewEffect.NavigateToCreateBorrowingPage
    }

    private fun fetchAllThings() {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            getThingsUseCase.invoke().fold({ flow ->
                flow.map { thingList -> thingList.map { it.toRecyclerItem() } }.collect {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, allThings = it)
                    _things.value = it
                }
            }, {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                viewEffect = AllThingsViewEffect.ShowToast(message = "Failed to fetch things.")
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).i("$TAG destroyed!")
    }
}