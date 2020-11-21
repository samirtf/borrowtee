package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import tf.samir.domain.entities.ThingEntity

data class AllThingsViewState(val fetchStatus: FetchStatus, val allThings: List<ThingEntity>)

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