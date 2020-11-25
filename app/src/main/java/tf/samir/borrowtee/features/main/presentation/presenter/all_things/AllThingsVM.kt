package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tf.samir.borrowtee.features.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.usecases.GetThingsUseCase
import timber.log.Timber

class AllThingsVM @ViewModelInject constructor(private val getThingsUseCase: GetThingsUseCase) :
    HyperViewModel<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent>() {

    companion object {
        const val TAG = "AllThingsVM"
    }

    init {
        viewState =
            AllThingsViewState(fetchStatus = FetchStatus.NotFetched, allThings = emptyList())
        Timber.tag(TAG).i("$TAG created!")
    }

    val things = liveData<List<RecyclerItem>> {
        emit(viewState.allThings)
    }

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