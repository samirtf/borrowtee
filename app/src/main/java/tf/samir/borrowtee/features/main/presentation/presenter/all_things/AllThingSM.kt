package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import tf.samir.borrowtee.viewbase.RecyclerItem

data class AllThingsViewState(val fetchStatus: FetchStatus, val allThings: List<RecyclerItem>)

sealed class AllThingsViewEffect {
    object ShowFetchThingsSuccess : AllThingsViewEffect()
    data class ShowFetchThingsFailure(val failure: Throwable) : AllThingsViewEffect()
    object ShowDeleteThingSuccess : AllThingsViewEffect()
    data class ShowDeleteThingFailure(val failure: Throwable) : AllThingsViewEffect()
    object NavigateToCreateBorrowingPage : AllThingsViewEffect()
}

sealed class AllThingsViewEvent {
    object FabClicked : AllThingsViewEvent()
    object FetchAllThings : AllThingsViewEvent()
    data class DeleteThingClicked(val position: Int) : AllThingsViewEvent()
    object FilterByAllThings : AllThingsViewEvent()
    object FilterByBorrowed : AllThingsViewEvent()
    object FilterByAtHome : AllThingsViewEvent()
    object SortUpward : AllThingsViewEvent()
    object SortDownward : AllThingsViewEvent()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}