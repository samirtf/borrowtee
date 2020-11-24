package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import tf.samir.domain.entities.ThingEntity

data class AllThingsViewState(val fetchStatus: FetchStatus, val allThings: List<ThingEntity>)

sealed class AllThingsViewEffect {
    data class ShowToast(val message: String) : AllThingsViewEffect()
    object NavigateToCreateBorrowingPage : AllThingsViewEffect()
}

sealed class AllThingsViewEvent {
    object FabClicked : AllThingsViewEvent()
    object FetchAllThingsView : AllThingsViewEvent()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}