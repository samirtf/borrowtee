package tf.samir.coreexample

data class ExampleViewState(val fetchStatus: FetchStatus, val itemsList: List<String>)

sealed class ExampleViewEffect {
    data class ShowSnackbar(val message: String) : ExampleViewEffect()
    data class ShowToast(val message: String) : ExampleViewEffect()
}

sealed class ExampleViewEvent {
    data class ExampleItemClicked(val exampleItem: String) : ExampleViewEvent()
    object FabClicked : ExampleViewEvent()
    object OnSwipeRefresh : ExampleViewEvent()
    object FetchExampleItems : ExampleViewEvent()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}