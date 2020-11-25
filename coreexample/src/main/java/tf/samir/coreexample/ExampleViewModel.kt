package tf.samir.coreexample

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tf.samir.core.base.AndroidHyperViewModel
import kotlin.random.Random

class ExampleViewModel(application: Application) :
    AndroidHyperViewModel<ExampleViewState, ExampleViewEffect, ExampleViewEvent>(application) {

    private var count: Int = 0
    private val repository = listOf("samir", "trajano", "feitosa")

    init {
        viewState = ExampleViewState(fetchStatus = FetchStatus.NotFetched, itemsList = emptyList())
    }

    override fun handle(viewEvent: ExampleViewEvent) {
        super.handle(viewEvent)
        when (viewEvent) {
            is ExampleViewEvent.ExampleItemClicked -> exampleItemClicked(viewEvent.exampleItem)
            ExampleViewEvent.FabClicked -> fabClicked()
            ExampleViewEvent.OnSwipeRefresh -> fetchExampleItems()
            ExampleViewEvent.FetchExampleItems -> fetchExampleItems()
        }
    }

    private fun exampleItemClicked(exampleItem: String) {
        viewEffect = ExampleViewEffect.ShowSnackbar(exampleItem)
    }

    private fun fabClicked() {
        count++
        viewEffect = ExampleViewEffect.ShowToast(message = "Fab clicked count $count")
    }

    private fun fetchExampleItems() {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            val result: LCE<List<String>> = if (Random.nextBoolean()) LCE.Success(data = repository) else LCE.Error(Exception("something went wrong"))
            when (result) {
                is LCE.Error<*> -> {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                    viewEffect = ExampleViewEffect.ShowToast(message = result.message)
                }
                is LCE.Success -> {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, itemsList = result.data)
                }
            }
        }
    }
}