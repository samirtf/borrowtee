package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tf.samir.borrowtee.BR
import tf.samir.core.base.HyperViewModel
import tf.samir.domain.entities.AT_HOME
import tf.samir.domain.entities.BORROWED
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
            withContext(Dispatchers.IO) {
                val result = performBorrowingCreation(thingData)
                result.fold({
                    withContext(Dispatchers.Main) {
                        viewState = viewState.buildCreatedState(DialogState.ShowingSuccess)
                    }
                }, {
                    withContext(Dispatchers.Main) {
                        viewState = viewState.buildNotCreatedState(DialogState.ShowingFailure(it))
                    }
                })
            }
        }
    }

    private suspend fun performBorrowingCreation(thingData: ThingData?): Result<Boolean> {
        thingData?.let {
            return createBorrowingUseCase.invoke(it.toThingEntity()
                .copy(state = if (Random.nextBoolean()) BORROWED else AT_HOME))
        } ?: return Result.failure(Exception("Fail to create thing."))
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

data class ThingData(private var _name: String, private var _description: String) : BaseObservable() {

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    var description: String
        @Bindable get() = _description
        set(value) {
            _description = value
            notifyPropertyChanged(BR.description)
        }
}

fun ThingData.toThingEntity(): ThingEntity {
    return ThingEntity(name = this.name, description = this.description)
}
