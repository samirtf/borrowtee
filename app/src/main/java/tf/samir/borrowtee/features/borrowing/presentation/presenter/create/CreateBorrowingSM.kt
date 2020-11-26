package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

data class CreateBorrowingViewState(
    val createStatus: CreateStatus = CreateStatus.NotCreated,
    val progressBar: Boolean = false,
    val isLayoutNameEnable: Boolean = true,
    val isLayoutDescriptionEnable: Boolean = true,
    val isOkButtonEnable: Boolean = true
)

sealed class CreateBorrowingViewEffect {
    object ShowSuccessDialog : CreateBorrowingViewEffect()
    object ShowFailureDialog : CreateBorrowingViewEffect()
    object NavigateBack : CreateBorrowingViewEffect()
}

sealed class CreateBorrowingViewEvent {
    data class CreateClicked(val thingData: ThingData?) : CreateBorrowingViewEvent()
    object Cancel : CreateBorrowingViewEvent()
}

sealed class CreateStatus {
    object Creating : CreateStatus()
    object Created : CreateStatus()
    object NotCreated : CreateStatus()
}

fun CreateBorrowingViewState.buildCreatingState(): CreateBorrowingViewState {
    return this.copy(
        createStatus = CreateStatus.Creating,
        progressBar = true,
        isLayoutNameEnable = false,
        isLayoutDescriptionEnable = false,
        isOkButtonEnable = false
    )
}

fun CreateBorrowingViewState.buildCreatedState(): CreateBorrowingViewState {
    return this.copy(
        createStatus = CreateStatus.Created,
        progressBar = false,
        isLayoutNameEnable = false,
        isLayoutDescriptionEnable = false,
        isOkButtonEnable = false
    )
}

fun CreateBorrowingViewState.buildNotCreatedState(): CreateBorrowingViewState {
    return this.copy(
        createStatus = CreateStatus.NotCreated,
        progressBar = false,
        isLayoutNameEnable = true,
        isLayoutDescriptionEnable = true,
        isOkButtonEnable = true
    )
}