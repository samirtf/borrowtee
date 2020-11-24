package tf.samir.borrowtee.features.main.presentation.presenter.all_things

import tf.samir.core.base.HyperViewModel

class AllThingsVM: HyperViewModel<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent>() {

    init {
        viewState = AllThingsViewState(fetchStatus = FetchStatus.NotFetched, allThings = emptyList())
    }

    override fun handle(viewEvent: AllThingsViewEvent) {
        super.handle(viewEvent)
        when (viewEvent) {
            is AllThingsViewEvent.FabClicked -> navigateToCreateBorrowing()

        }
    }

    private fun navigateToCreateBorrowing() {
        viewEffect = AllThingsViewEffect.NavigateToCreateBorrowingPage
    }

}