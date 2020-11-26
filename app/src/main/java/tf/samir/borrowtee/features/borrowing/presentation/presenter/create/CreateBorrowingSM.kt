package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

data class CreateBorrowingViewState(val createStatus: CreateStatus)

sealed class CreateBorrowingViewEffect {
    object ShowSuccessDialog : CreateBorrowingViewEffect()
    object ShowFailureDialog : CreateBorrowingViewEffect()
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