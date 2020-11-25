package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

data class CreateBorrowingViewState(val createStatus: CreateStatus)

sealed class CreateBorrowingViewEffect {
    data class ShowToast(val message: String) : CreateBorrowingViewEffect()
    object NavigateToAllThingsPage : CreateBorrowingViewEffect()
}

sealed class CreateBorrowingViewEvent {
    object CreateClicked : CreateBorrowingViewEvent()
}

sealed class CreateStatus {
    object Creating : CreateStatus()
    object Created : CreateStatus()
    object NotCreated : CreateStatus()
}