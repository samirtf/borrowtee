package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tf.samir.borrowtee.BR
import tf.samir.core.base.HyperViewModel
import timber.log.Timber
import kotlin.random.Random


class CreateBorrowingViewModel @ViewModelInject constructor() :
    HyperViewModel<CreateBorrowingViewState, CreateBorrowingViewEffect, CreateBorrowingViewEvent>() {

    companion object {
        const val TAG = "CreateBorrowingVM"
    }

    init {
        viewState = CreateBorrowingViewState(createStatus = CreateStatus.NotCreated)
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
        viewState = viewState.copy(createStatus = CreateStatus.Creating)
        Timber.tag(TAG).i("Creating borrowing...")
        Timber.tag(TAG).i("ThingData: ${thingData?.name ?: ""}")

        viewModelScope.launch {
            delay(3000)
            val result = performBorrowingCreation()
            result.fold({
                viewState = viewState.copy(createStatus = CreateStatus.Created)
                viewEffect = CreateBorrowingViewEffect.ShowSuccessDialog
            }, {
                viewState = viewState.copy(createStatus = CreateStatus.NotCreated)
                viewEffect = CreateBorrowingViewEffect.ShowFailureDialog
            })
        }
    }

    private fun performBorrowingCreation(): Result<Boolean> {
        return if (Random.nextBoolean().and(Random.nextBoolean())) {
            Result.failure(throw Exception("Fail to create thing."))
        } else {
            Result.success(true)
        }
    }

    private fun cancelCreation() {
        viewModelScope.launch {
            viewState = viewState.copy(createStatus = CreateStatus.NotCreated)
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


