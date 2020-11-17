package tf.samir.borrowtee.features.borrowing.presentation.presenter.create

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tf.samir.domain.entities.ThingEntity
import timber.log.Timber
import kotlin.random.Random


class CreateBorrowingViewModel() : ViewModel() {
    companion object {
        const val TAG = "CreateBorrowingVM"

    }

    enum class State {
        Idle, CreateBorrowing, Success, Error
    }

    private val borrowing: LiveData<ThingEntity>? = null

    private val _event = MutableLiveData(State.Idle)
    val event: LiveData<State>
        get() = _event

    init {
        Timber.tag(TAG).i( "$TAG created!")
    }

    fun createBorrowing() {
        _event.value = State.CreateBorrowing
        Timber.tag(TAG).i( "Creating borrowing...")
        performBorrowingCreation()
    }

    private fun performBorrowingCreation() {
        viewModelScope.launch {
            delay(3000)
            if(Random.nextBoolean() && Random.nextBoolean()) {
                _event.value = State.Error
            } else {
                _event.value = State.Success
            }
        }
    }

    fun onActionComplete() {
        Timber.tag(TAG).i( "Borrowing created!")
        _event.value = State.Idle
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(TAG).i("$TAG destroyed!")
    }

}


