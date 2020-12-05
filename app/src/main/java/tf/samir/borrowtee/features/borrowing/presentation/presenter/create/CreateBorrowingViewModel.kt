package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tf.samir.borrowtee.BR
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.usecases.CreateBorrowingUseCase
import timber.log.Timber
import kotlin.random.Random


class CreateBorrowingViewModel @ViewModelInject constructor(private val createBorrowingUseCase: CreateBorrowingUseCase) :
    HyperViewModel<CreateBorrowingViewState, CreateBorrowingViewEffect, CreateBorrowingViewEvent>() {

    companion object {
        const val TAG = "CreateBorrowingVM"
    }

    init {
        viewState = CreateBorrowingViewState()
        Timber.tag(TAG).i("$TAG created!")
    }

    override fun handle(viewEvent: CreateBorrowingViewEvent) {
        super.handle(viewEvent)
        when (viewEvent) {
            is CreateBorrowingViewEvent.CreateClicked -> createBorrowing(viewEvent.thingData)
            CreateBorrowingViewEvent.Cancel -> cancelCreation()
        }
    }

    private fun createBorrowing(thingData: ThingData?) {
        viewState = viewState.buildCreatingState()
        Timber.tag(TAG).i("Creating borrowing...")
        Timber.tag(TAG).i("ThingData: ${thingData?.name ?: ""}")

        viewModelScope.launch {
            delay(1000)
            val result = performBorrowingCreation(thingData)
            result.fold({
                viewState = viewState.buildCreatedState(DialogState.ShowingSuccess)
            }, {
                viewState = viewState.buildNotCreatedState(DialogState.ShowingFailure)
            })
        }
    }

    private suspend fun performBorrowingCreation(thingData: ThingData?): Result<Boolean> {
        thingData?.let { return createBorrowingUseCase.invoke(it.toThingEntity()) }
            ?: return Result.failure(Exception("Fail to create thing."))
    }

    private fun cancelCreation() {
        viewModelScope.launch {
            viewState = viewState.buildNotCreatedState()
            viewEffect = CreateBorrowingViewEffect.NavigateBack
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).i("$TAG destroyed!")
    }

}

data class ThingData(private var _name: String) : BaseObservable() {

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }
}

fun ThingData.toThingEntity(): ThingEntity {
    return ThingEntity(name = this.name)
}
