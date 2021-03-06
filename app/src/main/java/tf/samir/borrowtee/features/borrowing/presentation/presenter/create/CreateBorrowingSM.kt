package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

import java.lang.Exception

data class CreateBorrowingViewState(
    val createStatus: CreateStatus = CreateStatus.NotCreated,
    val progressBar: Boolean = false,
    val isLayoutNameEnable: Boolean = true,
    val isLayoutDescriptionEnable: Boolean = true,
    val isOkButtonEnable: Boolean = true,
    val dialogState: DialogState = DialogState.NotShowing
)

sealed class DialogState{
    object NotShowing : DialogState()
    object ShowingSuccess : DialogState()
    data class ShowingFailure(val exception: Throwable) : DialogState()
}

sealed class CreateBorrowingViewEffect {
    object ShowSuccessDialog : CreateBorrowingViewEffect()
    object ShowFailureDialog : CreateBorrowingViewEffect()
    object NavigateBack : CreateBorrowingViewEffect()
    object OpenTakePictureDialog : CreateBorrowingViewEffect()
}

sealed class CreateBorrowingViewEvent {
    data class CreateClicked(val thingData: ThingData?) : CreateBorrowingViewEvent()
    object Cancel : CreateBorrowingViewEvent()
    object TakePicture : CreateBorrowingViewEvent()
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
        isOkButtonEnable = false,
        dialogState = DialogState.NotShowing
    )
}

fun CreateBorrowingViewState.buildCreatedState(dialogState: DialogState = DialogState.NotShowing): CreateBorrowingViewState {
    return this.copy(
        createStatus = CreateStatus.Created,
        progressBar = false,
        isLayoutNameEnable = false,
        isLayoutDescriptionEnable = false,
        isOkButtonEnable = false,
        dialogState = dialogState
    )
}

fun CreateBorrowingViewState.buildNotCreatedState(dialogState: DialogState = DialogState.NotShowing): CreateBorrowingViewState {
    return this.copy(
        createStatus = CreateStatus.NotCreated,
        progressBar = false,
        isLayoutNameEnable = true,
        isLayoutDescriptionEnable = true,
        isOkButtonEnable = true,
        dialogState = dialogState
    )
}
