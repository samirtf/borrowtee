package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import tf.samir.borrowtee.viewbase.RecyclerItem

data class AllThingsViewState(val fetchStatus: FetchStatus, val allThings: List<RecyclerItem>)

sealed class AllThingsEffect {
    data class ShowToast(val message: String) : AllThingsEffect()
}

sealed class AllThingsEvent {
    object FabClicked : AllThingsEvent()
    object FetchAllThings : AllThingsEvent()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}