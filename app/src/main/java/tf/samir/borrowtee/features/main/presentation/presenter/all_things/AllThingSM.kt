package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import tf.samir.borrowtee.viewbase.RecyclerItem

data class AllThingsViewState(val fetchStatus: FetchStatus, val allThings: List<RecyclerItem>)

sealed class AllThingsViewEffect {
    data class ShowToast(val message: String) : AllThingsViewEffect()
    object NavigateToCreateBorrowingPage : AllThingsViewEffect()
}

sealed class AllThingsViewEvent {
    object FabClicked : AllThingsViewEvent()
    object FetchAllThings : AllThingsViewEvent()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}