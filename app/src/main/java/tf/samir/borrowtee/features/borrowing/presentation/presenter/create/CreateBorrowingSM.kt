package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

data class CreateBorrowingViewState(val createStatus: CreateStatus)

sealed class CreateBorrowingViewEffect {
    data class ShowDialog(val message: String) : CreateBorrowingViewEffect()
    object NavigateBack : CreateBorrowingViewEffect()
}

sealed class CreateBorrowingViewEvent {
    data class CreateClicked(val thingData: ThingData) : CreateBorrowingViewEvent()
    object Cancel : CreateBorrowingViewEvent()
}

sealed class CreateStatus {
    object Creating : CreateStatus()
    object Created : CreateStatus()
    object NotCreated : CreateStatus()
}