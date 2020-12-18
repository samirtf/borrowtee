package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tf.samir.borrowtee.features.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.usecases.DeleteBorrowingUseCase
import tf.samir.domain.usecases.GetThingsUseCase
import timber.log.Timber

class AllThingsViewModel @ViewModelInject constructor(
    private val getThingsUseCase: GetThingsUseCase,
    private val deleteBorrowingUseCase: DeleteBorrowingUseCase) :
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
            is AllThingsViewEvent.DeleteThingClicked -> deleteThingItem(viewEvent.position)
        }
    }

    private fun navigateToCreateBorrowing() {
        viewEffect = AllThingsViewEffect.NavigateToCreateBorrowingPage
    }

    private fun fetchAllThings() {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getThingsUseCase.invoke().fold({ flow ->
                    flow.map { thingList -> thingList.map { it.toRecyclerItem() } }.collect {
                        withContext(Dispatchers.Main) {
                            viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, allThings = it)
                            _things.value = it
                        }
                    }
                }, {
                    withContext(Dispatchers.Main) {
                        viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                        viewEffect = AllThingsViewEffect.ShowToast(message = "Failed to fetch things.")
                    }
                })
            }
        }
    }

    private fun deleteThingItem(id: Int) {
        things.value?.run {
            val item = get(id)
            viewModelScope.launch {
                viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
                withContext(Dispatchers.IO) {
                    deleteBorrowingUseCase.invoke(item.data.id).fold({
                        Timber.tag(TAG).d("$it")
                        withContext(Dispatchers.Main) {
                            viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                            viewEffect = AllThingsViewEffect.ShowToast(message = "Success to delete thing.")
                        }
                    }, {
                        Timber.tag(TAG).d("$it")
                        withContext(Dispatchers.Main) {
                            viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                            viewEffect = AllThingsViewEffect.ShowToast(message = "Failed to delete thing.")
                        }
                    })
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).i("$TAG destroyed!")
    }
}